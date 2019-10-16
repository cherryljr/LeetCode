/*
Given a date, return the corresponding day of the week for that date.
The input is given as three integers representing the day, month and year respectively.
Return the answer as one of the following values {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"}.

Example 1:
Input: day = 31, month = 8, year = 2019
Output: "Saturday"

Example 2:
Input: day = 18, month = 7, year = 1999
Output: "Sunday"

Example 3:
Input: day = 15, month = 8, year = 1993
Output: "Sunday"

Constraints:
    The given dates are valid dates between the years 1971 and 2100.
 */

/**
 * Approach: Zeller's Formula
 * 蔡勒公式：给定年月日，通过该公式可以计算得到当天是星期几。公式如下所示：
 *  w = x + [x / 4] + [c / 4] - 2c + [26(m + 1) / 10] + d - 1
 * 其中，c表示年份前两位，x表示年份后两位，m表示月，1月和2月看成上一年的13月和14月，d表示日。方括号表示向下取整。
 * 对w对7进行取模即可知道是星期几，余数是0表示星期日，余数是1到6分别表示星期一到星期六。
 * 需要注意的是W可能是负值，因此需要对 w 进行 (w%7+7)%7 的操作来保证其结果落在 0~6 上面。
 *
 * 注意：适用于1582年10月15日之后, 因为罗马教皇格里高利十三世在这一天启用新历法。
 *
 * 时间复杂度：O(1)
 * 空间复杂度：O(1)
 */
class Solution {
    public String dayOfTheWeek(int day, int month, int year) {
        final String[] DAYS = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        int dayIndex = getDayIndex(day, month, year);
        return DAYS[dayIndex];
    }

    public int getDayIndex(int day, int month, int year) {
        if (month < 3) {
            month += 12;
            year--;
        }
        int century = year / 100;
        year %= 100;
        int w = year + year / 4 + century / 4 - 2 * century + 26 * (month + 1) / 10 + day - 1;
        return (w % 7 + 7) % 7;
    }
}