package newPackage;

public final class GoodSuffixRule {
    private final String pattern;
    private final int patternLength;
    private final int[] table;

    public GoodSuffixRule(String pattern, int countIterations) {
        if (pattern == null || pattern.isEmpty()) {
            throw new IllegalArgumentException();
        }
        this.pattern = pattern;
        this.patternLength = pattern.length();
        this.table = new int[patternLength+1];
        buildTable(countIterations);
    }


    // Возвращает количество пропусков по правилу правильного суффикса
    // смещение - это количество символов слева от последнего индекса шаблона
    // например, ABCD -> Смещение D = 0, смещение C = 1 и т.д.
    // смещение должно быть на несовпадающий символ
    public int getNumSkips(int offset,int countIterations) {
        if (offset < 0 || offset > patternLength) {
            throw new IllegalArgumentException();
        }

        if (offset == 0) {
            return 0;
        }

        int index = patternLength - offset - 1;
        return table[index+1];
    }


    // Предварительная обработка для правила правильного суффикса
    private void buildTable(int countIterations) {
        int[] tmp = new int[patternLength+1];

        int i = patternLength;
        int j = patternLength + 1;

        tmp[i] = j;

        while (i > 0) {
            while (j <= patternLength && pattern.charAt(i-1) != pattern.charAt(j-1)) {
                countIterations++;
                if (table[j] == 0) {
                    table[j] = j - i;
                }
                j = tmp[j];
            }
            i--;
            j--;
            tmp[i] = j;
        }

        j = tmp[0];
        for (i=0; i<=patternLength; i++) {
            countIterations++;
            if (table[i] == 0) {
                table[i] = j;
            }
            if (i == j) {
                j = tmp[j];
            }
        }

        for (i=0; i<table.length; i++) {
            countIterations++;
            table[i]--;
        }
    }
}
