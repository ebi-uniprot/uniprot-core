package org.uniprot.core.flatfile.writer.impl;

import java.util.*;

public class FFLineWrapper {
    private static final String[] NOT_WRAPPED = {"->", "-->", "- ", "EC ", "TC ", "ECO:"};
    //	private final static String[] DASHS ={"->", "-->", "- "};
    private static final String DASH = "-";

    public static StringBuilder wrap(StringBuilder wrapThis, String separator, String linePrefix) {
        String[] seps = {separator};
        return wrap(wrapThis, seps, linePrefix);
    }

    public static StringBuilder wrap(
            StringBuilder wrapThis, String[] separators, String linePrefix) {
        return wrap(wrapThis, separators, linePrefix, FFLineConstant.LINE_LENGTH);
    }

    public static StringBuilder wrap(
            StringBuilder wrapThis, String[] separators, String linePrefix, int lineLength) {
        StringBuilder sb = new StringBuilder();
        boolean isFirst = true;
        for (String line : buildLines(wrapThis.toString(), separators, linePrefix, lineLength)) {
            if (!isFirst) sb.append('\n');
            sb.append(line);
            isFirst = false;
        }
        return sb;
    }

    public static List<String> buildLines(
            StringBuilder wrapThis, String separator, String linePrefix) {
        return buildLines(wrapThis.toString(), separator, linePrefix);
    }

    public static List<String> buildLines(String wrapThis, String separator, String linePrefix) {
        return buildLines(wrapThis, separator, linePrefix, FFLineConstant.LINE_LENGTH);
    }

    public static List<String> buildLines(
            String wrapThis, String separator, String linePrefix, int lineLength) {
        if (separator.isEmpty()) {
            return buildLinesNoSeparator(wrapThis, linePrefix, lineLength);
        }
        String[] seps = {separator};
        return buildLines(wrapThis, seps, linePrefix, lineLength);
    }

    public static List<String> buildLinesNoSeparator(
            String wrapThis, String linePrefix, int lineLength) {
        List<String> lines = new ArrayList<>();
        if (wrapThis.isEmpty()) return lines;
        String tempStr = wrapThis;
        while (tempStr.length() > lineLength) {
            String line = tempStr.substring(0, lineLength);
            lines.add(line);
            tempStr = linePrefix + tempStr.substring(lineLength);
            if (tempStr.length() <= lineLength) {
                break;
            }
        }
        ;
        if (tempStr.length() <= lineLength) {
            lines.add(tempStr);
            return lines;
        }
        return lines;
    }

    public static List<String> buildLines(
            String wrapThis, String[] separators, String linePrefix, int lineLength) {
        List<String> lines = new ArrayList<>();
        if (wrapThis.isEmpty()) return lines;
        String tempStr = wrapThis;
        int startPoint = linePrefix.length();
        if (tempStr.length() <= lineLength) {
            lines.add(tempStr);
            return lines;
        }
        while (tempStr.length() > lineLength) {
            Map.Entry<Integer, String> indexSep =
                    getIndex(tempStr, separators, lineLength, startPoint, true);

            int index = indexSep.getKey();
            if (index <= startPoint) {
                indexSep = getIndex(tempStr, separators, lineLength, startPoint, false);
            }
            index = indexSep.getKey();
            String separator = indexSep.getValue();
            if (index <= startPoint) {
                lines.add(tempStr);
                break;
            }
            String line = tempStr.substring(0, index + separator.length()).trim();
            lines.add(line);
            tempStr = linePrefix + tempStr.substring(index + separator.length());
            if (tempStr.length() <= lineLength) {
                lines.add(tempStr);
                break;
            }
        }
        ;

        return lines;
    }

    public static List<String> buildLine(
            List<String> tokens,
            String separator,
            String space,
            String stop,
            String linePrefix,
            int lineLength,
            boolean wrapping) {
        List<String> lines = new ArrayList<>();
        if (tokens.size() == 0) return lines;
        int count = 0;
        StringBuilder line = new StringBuilder();
        line.append(linePrefix);

        for (String token : tokens) {
            count++;
            if (wrapping
                    && ((line.length() + token.length() + separator.length())
                            >= FFLineConstant.LINE_LENGTH)) {
                lines.add(line.toString());
                line = new StringBuilder(linePrefix);
            } else if (count != 1) line.append(space);

            line.append(token);

            if (count < tokens.size()) line.append(separator);
            else line.append(stop);
        }
        if (line.length() > 0) lines.add(line.toString());
        return lines;
    }

    private static Map.Entry<Integer, String> getIndex(
            String str, String[] separators, int lineLength, int startPoint, boolean fromLast) {
        int index = 0;
        String sep = "";
        //	Set<Integer> notWrapped = getNotWrapped(str, startPoint);
        Map<String, Set<Integer>> notWrappedMap = getNotWrappedMap(str, startPoint);
        for (String separator : separators) {
            int sepLength = ("a" + separator).trim().length() - 1;
            if (fromLast) {
                int index2 =
                        getWrappingPoint(
                                str,
                                separator,
                                lineLength - sepLength,
                                startPoint,
                                true,
                                notWrappedMap);
                if (index2 > index) {
                    index = index2;
                    sep = separator;
                }
            } else {
                int index2 =
                        getWrappingPoint(
                                str,
                                separator,
                                lineLength - sepLength,
                                startPoint,
                                false,
                                notWrappedMap);
                if (index == 0) {
                    index = index2;
                    sep = separator;
                } else if (index2 < index) {
                    index = index2;
                    sep = separator;
                }
            }
        }
        return new AbstractMap.SimpleEntry<Integer, String>(index, sep);
    }

    private static int getWrappingPoint(
            String str,
            String sep,
            int lineLength,
            int startPoint,
            boolean last,
            Map<String, Set<Integer>> notWrapped) {
        boolean isSpace = sep.equals(" ");
        if (last) {
            int end = lineLength;
            int index2 = str.lastIndexOf(sep, end);
            if (index2 == -1) return index2;
            while (isContain(index2, notWrapped, isSpace)) {
                end = index2;
                end -= sep.length();
                index2 = str.lastIndexOf(sep, end);
                if (index2 == -1) return index2;
            }
            ;
            return index2;
        } else {
            int start = startPoint;
            int index2 = str.indexOf(sep, start);
            if (index2 == -1) return index2;
            while (isContain(index2, notWrapped, isSpace)) {
                start = index2;
                start += sep.length();
                index2 = str.indexOf(sep, start);
                if (index2 == -1) return index2;
            }
            return index2;
        }
    }

    private static boolean isContain(
            int val, Map<String, Set<Integer>> notWrapped, boolean isSpace) {
        if (notWrapped.isEmpty()) return false;
        for (Map.Entry<String, Set<Integer>> entry : notWrapped.entrySet()) {
            int len = entry.getKey().length();
            for (int index : entry.getValue()) {
                if (val == index) return true;
                if ((val > index) && (val < (index + len) && (!isSpace))) return true;
                if ((val == (index + len - 1)) && isSpace) return true;
            }
        }
        return false;
    }

    private static Map<String, Set<Integer>> getNotWrappedMap(String val, int start) {
        Map<String, Set<Integer>> notWrapped = new TreeMap<>();

        int start1 = start;
        for (String s : NOT_WRAPPED) {
            int index = 0;
            LinkedHashSet<Integer> indices = new LinkedHashSet<>();
            do {

                index = val.indexOf(s, start1);
                if (index == -1) break;
                indices.add(index);
                start1 = index;
                start1 += s.length();

            } while (index != -1);
            if (!indices.isEmpty()) {
                notWrapped.put(s, indices);
            }
        }
        addDashSpecial(notWrapped, val, start);
        return notWrapped;
    }

    private static void addDashSpecial(
            Map<String, Set<Integer>> notWrapped, String val, int start) {
        int start1 = start;
        int length = val.length();
        int index = 0;
        boolean notWrappedIsEmpty = notWrapped.isEmpty();
        do {
            index = val.indexOf(DASH, start1);
            if ((index == -1) || (index == (length - 1))) break;
            String found = notWrappedIsEmpty ? null : isInNotWrapped(notWrapped, index);

            String digit = getDigitMap(val, index);

            if (found != null) {
                start1 += found.length();
                continue;
            } else if (digit != null) {
                String dashSp = DASH + digit;
                start1 = index;
                start1 += digit.length();
                Set<Integer> found2 = notWrapped.get(dashSp);
                if (found2 == null) {
                    found2 = new LinkedHashSet<>();
                    notWrapped.put(dashSp, found2);
                    notWrappedIsEmpty = false;
                }
                found2.add(index);

            } else if (isSpaceBeforeDash(val, index)) {
                String dashSp = " " + DASH;
                start1 += 1;
                Set<Integer> found2 = notWrapped.get(dashSp);
                if (found2 == null) {
                    found2 = new LinkedHashSet<>();
                    notWrapped.put(dashSp, found2);
                    notWrappedIsEmpty = false;
                }
                found2.add(index);
            } else if (isSpecialCharacter(val.charAt(index + 1))) {
                String dashSp = DASH + val.charAt(index + 1);
                start1 += 2;
                Set<Integer> found2 = notWrapped.get(dashSp);
                if (found2 == null) {
                    found2 = new LinkedHashSet<>();
                    notWrapped.put(dashSp, found2);
                    notWrappedIsEmpty = false;
                }
                found2.add(index);
            } else {
                start1 += 2;
            }

        } while (index != -1);
    }

    private static boolean isSpaceBeforeDash(String val, int index) {
        if (index <= 0) return false;
        if (val.charAt(index - 1) == ' ') {
            if (val.length() > index + 1) {
                if (val.charAt(index + 1) == ' ') {
                    return false;
                } else return true;
            } else return true;
        }
        return false;
    }

    private static String getDigit(String val, int index) {
        int nextIndex = -1;

        if (val.indexOf(' ', index) != -1) {
            nextIndex = val.indexOf(' ', index);
        }
        if (val.indexOf(',', index) != -1) {
            int nextIndex2 = val.indexOf(',', index);
            if (nextIndex == -1) {
                nextIndex = nextIndex2;
            } else nextIndex = Math.min(nextIndex, nextIndex2);
        }

        if (val.indexOf(';', index) != -1) {
            int nextIndex2 = val.indexOf(';', index);
            if (nextIndex == -1) {
                nextIndex = nextIndex2;
            } else nextIndex = Math.min(nextIndex, nextIndex2);
        }
        if (val.indexOf('.', index) != -1) {
            int nextIndex2 = val.indexOf('.', index);
            if (nextIndex == -1) {
                nextIndex = nextIndex2;
            } else nextIndex = Math.min(nextIndex, nextIndex2);
        }
        if (nextIndex == -1) return null;

        String sub = val.substring(index + 1, nextIndex);
        for (int i = 0; i < sub.length(); i++) {
            char c = sub.charAt(i);
            if (!Character.isDigit(c)) return null;
        }
        return sub;
    }

    private static String getDigitMap(String val, int index) {
        int nextIndex = -1;

        int printableChars = 126 - 32;
        int[] charToPosition = new int[printableChars];
        Arrays.fill(charToPosition, -1);
        for (int i = 0; i < val.length() && i < FFLineConstant.LINE_LENGTH; i++) {
            if (charToPosition[val.charAt(i) - 32] == -1) {
                charToPosition[val.charAt(i) - 32] = index + i;
            }
        }

        //        Map<Character, Integer> characterPositions = new HashMap<>();

        //        for (int i = 0; i < val.length() && i < FFLineConstant.LINE_LENGTH; i++) {
        //            if (!characterPositions.containsKey(val.charAt(i))) {
        //                characterPositions.put(val.charAt(i), index + i);
        //            }
        //        }
        if (charToPosition[' ' - 32] != -1) {
            nextIndex = charToPosition[' ' - 32];
        }
        if (charToPosition[',' - 32] != -1) {
            int nextIndex2 = charToPosition[',' - 32];
            if (nextIndex == -1) {
                nextIndex = nextIndex2;
            } else nextIndex = Math.min(nextIndex, nextIndex2);
        }

        if (charToPosition[';' - 32] != -1) {
            int nextIndex2 = charToPosition[';' - 32];
            if (nextIndex == -1) {
                nextIndex = nextIndex2;
            } else nextIndex = Math.min(nextIndex, nextIndex2);
        }
        if (charToPosition['.' - 32] != -1) {
            int nextIndex2 = charToPosition['.' - 32];
            if (nextIndex == -1) {
                nextIndex = nextIndex2;
            } else nextIndex = Math.min(nextIndex, nextIndex2);
        }
        if (nextIndex == -1) return null;

        String sub = val.substring(index + 1, nextIndex);
        for (int i = 0; i < sub.length(); i++) {
            char c = sub.charAt(i);
            if (!Character.isDigit(c)) return null;
        }
        return sub;
    }

    private static String isInNotWrapped(Map<String, Set<Integer>> notWrapped, int index) {
        for (Map.Entry<String, Set<Integer>> entry : notWrapped.entrySet()) {
            if (entry.getValue().contains(index)) {
                return entry.getKey();
            }
        }
        return null;
    }

    private static boolean isSpecialCharacter(char c) {
        boolean isChar = Character.isDigit(c) || Character.isLetter(c) || (c == '(') || (c == '[');
        return !isChar;
    }
}
