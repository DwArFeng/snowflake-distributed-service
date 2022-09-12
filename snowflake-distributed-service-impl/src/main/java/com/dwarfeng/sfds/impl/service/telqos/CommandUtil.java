package com.dwarfeng.sfds.impl.service.telqos;

import java.util.StringJoiner;

/**
 * 指令工具类。
 *
 * @author DwArFeng
 * @since 1.4.9
 */
public final class CommandUtil {

    /**
     * 拼接选项的前缀，用于生成选项说明书。
     *
     * <p>
     * online -> -online<br>
     * dump-file -> --dump-file。
     *
     * @param commandOption 指定的选项。
     * @return 拼接前缀之后的选项。
     */
    public static String concatOptionPrefix(String commandOption) {
        if (commandOption.contains("-")) {
            return "--" + commandOption;
        }
        return "-" + commandOption;
    }

    public static String syntax(String... patterns) {
        StringJoiner sj = new StringJoiner(System.lineSeparator());
        for (String pattern : patterns) {
            sj.add(pattern);
        }
        return sj.toString();
    }

    public static String optionMismatchMessage(String... patterns) {
        StringJoiner sj = new StringJoiner(", --", "下列选项必须且只能含有一个: --", "");
        for (String pattern : patterns) {
            sj.add(pattern);
        }
        return sj.toString();
    }

    private CommandUtil() {
        throw new IllegalStateException("禁止实例化");
    }
}
