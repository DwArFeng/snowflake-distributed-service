package com.dwarfeng.sfds.impl.service.telqos;

import com.dwarfeng.sfds.stack.service.LongIdService;
import com.dwarfeng.springtelqos.sdk.command.CliCommand;
import com.dwarfeng.springtelqos.stack.command.Context;
import com.dwarfeng.springtelqos.stack.exception.TelqosException;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GenCommand extends CliCommand {

    private static final String IDENTITY = "gen";
    private static final String DESCRIPTION = "生成ID并导出到CSV操作";

    private static final String CMD_OPTION_NUMBER = "n";
    private static final String CMD_OPTION_FILE = "f";

    private static final String CMD_LINE_SYNTAX_INTERACTIVELY = "gen";
    private static final String CMD_LINE_SYNTAX_NUMBER = "gen -" + CMD_OPTION_NUMBER + " [number]";
    private static final String CMD_LINE_SYNTAX = CMD_LINE_SYNTAX_INTERACTIVELY + System.lineSeparator() +
            CMD_LINE_SYNTAX_NUMBER;

    private final LongIdService longIdService;

    public GenCommand(LongIdService longIdService) {
        super(IDENTITY, DESCRIPTION, CMD_LINE_SYNTAX);
        this.longIdService = longIdService;
    }

    @Override
    protected List<Option> buildOptions() {
        List<Option> list = new ArrayList<>();
        list.add(Option.builder(CMD_OPTION_NUMBER).optionalArg(true).hasArg(true).type(Number.class)
                .argName("number").desc("生成数量").build());
        return list;
    }

    @Override
    protected void executeWithCmd(Context context, CommandLine cmd) throws TelqosException {
        try {
            int number;
            if (cmd.hasOption(CMD_OPTION_NUMBER)) {
                number = ((Number) cmd.getParsedOptionValue(CMD_OPTION_NUMBER)).intValue();
            } else {
                context.sendMessage("请输入需要生成的 ID 的数量:");
                number = Integer.parseInt(context.receiveMessage());
            }
            int digits = digits(number - 1);
            List<Long> longs = longIdService.nextLongId(number);
            for (int i = 0; i < longs.size(); i++) {
                context.sendMessage(String.format("%-" + digits + "d: %d", i, longs.get(i)));
            }
        } catch (Exception e) {
            throw new TelqosException(e);
        }
    }

    private int digits(int target) {
        int digits = 0;
        do {
            digits++;
        } while ((target /= 10) > 0);
        return digits;
    }
}
