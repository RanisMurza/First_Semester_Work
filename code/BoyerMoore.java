package newPackage;

public final class BoyerMoore {
    private final String pattern;
    private final int patternLength;
    private final BadCharacterRule badCharacterRule;
    private final GoodSuffixRule goodSuffixRule;
    private int countIterations;

    public BoyerMoore(String pattern) {
        if (pattern == null || pattern.isEmpty()) {
            throw new IllegalArgumentException();
        }
        this.countIterations = 0;
        this.pattern = pattern;
        this.patternLength = pattern.length();
        this.badCharacterRule = new BadCharacterRule(pattern, countIterations);
        this.goodSuffixRule = new GoodSuffixRule(pattern, countIterations);
    }

    //Возвращает количество совпадений этого шаблона с текстом
    public int run(String text) {
        if (text == null) {
            throw new IllegalArgumentException();
        }

        final int textLength = text.length();
        if (textLength == 0 || text.length() < patternLength) {
            return 0;
        }

        int matches = 0;
        int index = patternLength - 1;
        while (index < textLength) {
            int offset = 0;
            while (offset < patternLength) {
                countIterations++;
                if (pattern.charAt(patternLength - offset - 1) != text.charAt(index - offset)) {
                    // Несоответствие, постараемся пропустить как можно больше
                    int badCharacterRuleSkips = badCharacterRule.getNumSkips(text.charAt(index - offset), offset, countIterations);
                    int goodSuffixRuleSkips = goodSuffixRule.getNumSkips(offset,countIterations);
                    index += Math.max(badCharacterRuleSkips, goodSuffixRuleSkips);
                    break;
                }
                offset++;
            }

            if (offset == patternLength) {
                matches++;
                // Правило правильного суффикса по-прежнему применяется и может привести к пропускам
                //Правило плохого символа не применяется, поскольку все символы совпадают (т.е. плохих символов нет).
                index += goodSuffixRule.getNumSkips(offset, countIterations);
            }
            index++;
        }
        return matches;
    }

    public String getPattern() {
        return pattern;
    }
    public int getCountIterations() {
        return countIterations;
    }
}
