package newPackage;

import java.util.HashMap;
import java.util.Map;

public final class BadCharacterRule {
    private final String pattern;
    private final int patternLength;
    private final Map<Character, int[]> table;

    public BadCharacterRule(String pattern, int countIterations) {
        if (pattern == null || pattern.isEmpty()) {
            throw new IllegalArgumentException();
        }
        this.pattern = pattern;
        this.patternLength = pattern.length();
        this.table = new HashMap<>();
        buildTable(countIterations);
    }

    // Возвращает количество пропусков по правилу плохого символа
    // смещение - это количество символов слева от последнего индекса шаблона
    // например, ABCD -> Смещение D = 0, смещение C = 1 и т.д.
    // c - это символ текста, в котором произошло несоответствие
    public int getNumSkips(char c, int offset, int countIterations) {
        if (offset < 0 || offset > patternLength) {
            throw new IllegalArgumentException();
        }

        // Правило плохого символа не применяется (все символы совпадают)
        if (offset == patternLength) {
            return 0;
        }

        int index = patternLength - offset - 1;
        if (!table.containsKey(c)) {
            return index;
        }
        return table.get(c)[index];
    }

    // Предварительная обработка для правила неверного символа
    private void buildTable(int countIterations) {
        for (int i=0; i<patternLength; i++) {
            countIterations++;
            char curr = pattern.charAt(i);
            if (!table.containsKey(curr)) {
                table.put(curr, new int[patternLength]);
            }
            table.get(curr)[i] = -1;
        }

        for (char c : table.keySet()) {
            int[] row = table.get(c);
            int counter = 0;
            for (int i=0; i<row.length; i++) {
                countIterations++;
                if (row[i] == -1) {
                    row[i] = counter;
                    counter = 0;
                } else {
                    row[i] = counter;
                    counter++;
                }
            }
        }
    }
}
