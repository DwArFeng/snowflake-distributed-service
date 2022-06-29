package com.dwarfeng.sfds.impl.service.telqos;

import com.dwarfeng.sfds.stack.service.LongIdService;
import com.dwarfeng.springtelqos.sdk.command.CliCommand;
import com.dwarfeng.springtelqos.stack.command.Context;
import com.dwarfeng.springtelqos.stack.exception.TelqosException;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

@Component
public class GenCsvCommand extends CliCommand {

    private static final String IDENTITY = "gencsv";
    private static final String DESCRIPTION = "生成ID并导出到CSV操作";

    private static final String CMD_OPTION_NUMBER = "n";
    private static final String CMD_OPTION_FILE = "f";

    private static final String CMD_LINE_SYNTAX_INTERACTIVELY = "gencsv";
    private static final String CMD_LINE_SYNTAX_NUMBER = "gencsv -" + CMD_OPTION_NUMBER + " [number]";
    private static final String CMD_LINE_SYNTAX_FILE = "gencsv -" + CMD_OPTION_FILE + " [file-path]";
    private static final String CMD_LINE_SYNTAX = CMD_LINE_SYNTAX_INTERACTIVELY + System.lineSeparator() +
            CMD_LINE_SYNTAX_NUMBER + System.lineSeparator() + CMD_LINE_SYNTAX_FILE;

    private static final String DEFAULT_FILE_NAME = "export.csv";

    private final LongIdService longIdService;

    public GenCsvCommand(LongIdService longIdService) {
        super(IDENTITY, DESCRIPTION, CMD_LINE_SYNTAX);
        this.longIdService = longIdService;
    }

    @Override
    protected List<Option> buildOptions() {
        List<Option> list = new ArrayList<>();
        list.add(Option.builder(CMD_OPTION_NUMBER).optionalArg(true).hasArg(true).type(Number.class)
                .argName("number").desc("生成数量").build());
        list.add(Option.builder(CMD_OPTION_FILE).optionalArg(true).hasArg(true).type(String.class)
                .argName("file-path").desc("生成的CSV的路径").build());
        return list;
    }

    @Override
    protected void executeWithCmd(Context context, CommandLine cmd) throws TelqosException {
        try {
            int number;
            File file;
            if (cmd.hasOption(CMD_OPTION_NUMBER)) {
                number = ((Number) cmd.getParsedOptionValue(CMD_OPTION_NUMBER)).intValue();
            } else {
                context.sendMessage("请输入需要生成的 ID 的数量:");
                number = Integer.parseInt(context.receiveMessage());
                context.sendMessage("");
            }
            if (cmd.hasOption(CMD_OPTION_FILE)) {
                file = new File(((String) cmd.getParsedOptionValue(CMD_OPTION_NUMBER)));
            } else {
                context.sendMessage("请输入需要导出的 CSV 的位置:");
                file = new File(context.receiveMessage());
            }
            if (file.isDirectory()) {
                file = new File(file, DEFAULT_FILE_NAME);
            }
            try (
                    FileWriter fw = new FileWriter(file);
                    BufferedWriter bw = new BufferedWriter(fw, 4096)
            ) {
                List<Long> longs = longIdService.nextLongId(number);
                for (Long aLong : longs) {
                    bw.write(Long.toString(aLong));
                    bw.write(System.lineSeparator());
                }
                bw.flush();
            }
            context.sendMessage("CSV文件已导出至 " + file.getAbsolutePath());
        } catch (Exception e) {
            throw new TelqosException(e);
        }
    }
}
