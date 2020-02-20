package org.uniprot.cv.ec;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.uniprot.core.cv.ec.ECEntry;
import org.uniprot.core.cv.ec.builder.ECEntryBuilder;
import org.uniprot.cv.common.AbstractFileReader;

public class ECFileReader extends AbstractFileReader<ECEntry> {
    private static final Pattern ENZYME_CLASS_PATTERN =
            Pattern.compile(
                    "^([1-9-][0-9]*\\.) ?([1-9-][0-9]*\\.) ?([1-9-][0-9]*\\.) ?([1-9-][0-9]*) ? +(.*)\\.$");
    private static final Pattern TERMINATING_DOTS = Pattern.compile("^(.*?)\\.*$");
    private final ECDatFileReader datFileReader;
    private final ECClassFileReader classFileReader;

    public ECFileReader() {
        this.datFileReader = new ECDatFileReader();
        this.classFileReader = new ECClassFileReader();
    }

    @Override
    public List<ECEntry> parse(String dirLocation) {
        List<ECEntry> ecList = new ArrayList<>();
        ecList.addAll(classFileReader.parse(dirLocation + ECCache.ENZCLASS_TXT));
        ecList.addAll(datFileReader.parse(dirLocation + ECCache.ENZYME_DAT));
        return ecList;
    }

    @Override
    public List<ECEntry> parseLines(List<String> lines) {
        throw new UnsupportedOperationException(
                "Functionality supplied by inner classes; please call 'parse' method.");
    }

    public static String removeLastDotsFrom(String line) {
        Matcher matcher = TERMINATING_DOTS.matcher(line);
        // no need to check if regex matched, since it always matches something
        matcher.matches();
        return matcher.group(1);
    }

    public static class ECClassFileReader extends AbstractFileReader<ECEntry> {
        @Override
        public List<ECEntry> parseLines(List<String> lines) {
            List<ECEntry> ecs = new ArrayList<>();
            for (String line : lines) {
                Matcher matcher = ENZYME_CLASS_PATTERN.matcher(line);
                if (matcher.matches() && matcher.groupCount() == 5) {
                    String id =
                            matcher.group(1)
                                    + matcher.group(2)
                                    + matcher.group(3)
                                    + matcher.group(4);
                    ecs.add(
                            new ECEntryBuilder()
                                    .id(id)
                                    .label(removeLastDotsFrom(matcher.group(5)))
                                    .build());
                }
            }
            return ecs;
        }
    }

    public static class ECDatFileReader extends AbstractFileReader<ECEntry> {
        @Override
        public List<ECEntry> parseLines(List<String> lines) {
            List<ECEntry> ecs = new ArrayList<>();
            ECEntryBuilder builder = new ECEntryBuilder();
            for (String line : lines) {
                if (line.startsWith("ID")) {
                    builder.id(removePrefixFrom(line));
                } else if (line.startsWith("DE")) {
                    builder.label(removeLastDotsFrom(removePrefixFrom(line)));
                }
                if (line.startsWith("//")) {
                    ECEntry ec = builder.build();
                    if (Objects.nonNull(ec.getId()) && Objects.nonNull(ec.getLabel())) {
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
