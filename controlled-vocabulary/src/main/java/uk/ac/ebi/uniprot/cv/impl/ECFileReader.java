package uk.ac.ebi.uniprot.cv.impl;

import uk.ac.ebi.uniprot.cv.ec.EC;
import uk.ac.ebi.uniprot.cv.ec.ECCache;
import uk.ac.ebi.uniprot.cv.ec.impl.ECImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ECFileReader extends AbstractFileReader<EC> {
    private static final Pattern ENZYME_CLASS_PATTERN = Pattern
            .compile("^([1-9-]\\.) ?([1-9-]\\.) ?([1-9-]\\.) ?([1-9-]) ? +(.*)\\.$");
    private static final Pattern TERMINATING_DOTS = Pattern.compile("^(.*?)\\.*$");
    private final ECDatFileReader datFileReader;
    private final ECClassFileReader classFileReader;

    public ECFileReader() {
        this.datFileReader = new ECDatFileReader();
        this.classFileReader = new ECClassFileReader();
    }

    @Override
    public List<EC> parse(String dirLocation) {
        List<EC> ecList = new ArrayList<>();
        ecList.addAll(datFileReader.parse(dirLocation + ECCache.ENZYME_DAT));
        ecList.addAll(classFileReader.parse(dirLocation + ECCache.ENZCLASS_TXT));
        return ecList;
    }

    @Override
    List<EC> parseLines(List<String> lines) {
        throw new UnsupportedOperationException("Functionality supplied by inner classes; please call 'parse' method.");
    }

    static String removeLastDotsFrom(String line) {
        Matcher matcher = TERMINATING_DOTS.matcher(line);
        if (matcher.matches()) {
            return matcher.group(1);
        } else {
            return line;
        }
    }

    private static class ECClassFileReader extends AbstractFileReader<EC> {
        @Override
        List<EC> parseLines(List<String> lines) {
            List<EC> ecs = new ArrayList<>();
            for (String line : lines) {
                Matcher matcher = ENZYME_CLASS_PATTERN.matcher(line);
                if (matcher.matches() && matcher.groupCount() == 5) {
                    String id = matcher.group(1) + matcher.group(2) + matcher.group(3) + matcher.group(4);
                    ecs.add(new ECImpl(id, removeLastDotsFrom(matcher.group(5))));
                }
            }
            return ecs;
        }
    }

    private static class ECDatFileReader extends AbstractFileReader<EC> {
        @Override
        List<EC> parseLines(List<String> lines) {
            List<EC> ecs = new ArrayList<>();
            ECImpl.Builder builder = new ECImpl.Builder();
            for (String line : lines) {
                if (line.startsWith("ID")) {
                    builder.id(removePrefixFrom(line));
                } else if (line.startsWith("DE")) {
                    builder.label(removeLastDotsFrom(removePrefixFrom(line)));
                }
                if (line.startsWith("//")) {
                    EC ec = builder.build();
                    if (Objects.nonNull(ec.id()) && Objects.nonNull(ec.label())) {
                        ecs.add(ec);
                    }
                }
            }
            return ecs;
        }

        private String removePrefixFrom(String line) {
            return line.substring(5);
        }
    }
}
