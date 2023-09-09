package com.dwarfeng.sfds.impl.service.telqos;

import com.dwarfeng.sfds.stack.service.GenerateQosService;
import com.dwarfeng.springtelqos.node.config.TelqosCommand;
import com.dwarfeng.springtelqos.sdk.command.CliCommand;
import com.dwarfeng.springtelqos.stack.command.Context;
import com.dwarfeng.springtelqos.stack.exception.TelqosException;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@TelqosCommand
public class GenCommand extends CliCommand {

    private static final String IDENTITY = "gen";
    private static final String DESCRIPTION = "生成ID并导出到CSV操作";

    private static final String CMD_OPTION_SIZE = "s";
    private static final String CMD_OPTION_FILE = "f";

    private static final String CMD_LINE_SYNTAX_INTERACTIVELY = IDENTITY;
    private static final String CMD_LINE_SYNTAX_NORMAL = IDENTITY + " "
            + CommandUtil.concatOptionPrefix(CMD_OPTION_SIZE) + " [size] ["
            + CommandUtil.concatOptionPrefix(CMD_OPTION_FILE) + " [file-path]]";

    private static final String[] CMD_LINE_ARRAY = new String[]{
            CMD_LINE_SYNTAX_INTERACTIVELY,
            CMD_LINE_SYNTAX_NORMAL
    };

    private static final String CMD_LINE_SYNTAX = CommandUtil.syntax(CMD_LINE_ARRAY);

    private static final int DEFAULT_SIZE = 10;
    private static final String DEFAULT_FILE_NAME = "export.csv";

    private final GenerateQosService generateQosService;

    public GenCommand(GenerateQosService generateQosService) {
        super(IDENTITY, DESCRIPTION, CMD_LINE_SYNTAX);
        this.generateQosService = generateQosService;
    }

    @Override
    protected List<Option> buildOptions() {
        List<Option> list = new ArrayList<>();
        list.add(Option.builder(CMD_OPTION_SIZE).optionalArg(true).hasArg(true).type(Number.class)
                .argName("size").desc("生成数量").build());
        list.add(Option.builder(CMD_OPTION_FILE).optionalArg(true).hasArg(true).type(String.class)
                .argName("file-path").desc("生成的CSV的路径").build());
        return list;
    }

    @Override
    protected void executeWithCmd(Context context, CommandLine cmd) throws TelqosException {
        try {
            int size;
            if (cmd.hasOption(CMD_OPTION_SIZE)) {
                Number sizeNumber = (Number) cmd.getParsedOptionValue(CMD_OPTION_SIZE);
                if (Objects.isNull(sizeNumber)) {
                    size = DEFAULT_SIZE;
                } else {
                    size = sizeNumber.intValue();
                }
            } else {
                context.sendMessage("请输入需要生成的 ID 的数量:");
                size = Integer.parseInt(context.receiveMessage());
            }
            List<Long> longs = generateQosService.generateLong(size);

            if (cmd.hasOption(CMD_OPTION_FILE)) {
                print2File(longs, context, cmd);
            } else {
                print2Screen(longs, context);
            }
        } catch (Exception e) {
            throw new TelqosException(e);
        }
    }

    private void print2File(List<Long> longs, Context context, CommandLine cmd) throws Exception {
        File file;

        String optionFileName = (String) cmd.getParsedOptionValue(CMD_OPTION_FILE);
        if (Objects.nonNull(optionFileName)) {
            file = new File((optionFileName));
            if (file.isDirectory()) {
                file = new File(file, DEFAULT_FILE_NAME);
            }
        } else {
            file = new File(DEFAULT_FILE_NAME);
        }

        if (file.exists()) {
            while (true) {
                context.sendMessage("文件 \"" + file.getName() + "\" 已经存在，是否覆盖？ Y/N");
                String option = context.receiveMessage().toUpperCase();
                if (Objects.equals(option, "Y")) {
                    break;
                } else if (Objects.equals(option, "N")) {
                    context.sendMessage("操作已取消！");
                    return;
                }
            }
        }

        try (
                FileWriter fw = new FileWriter(file);
                BufferedWriter bw = new BufferedWriter(fw, 4096)
        ) {
            for (Long aLong : longs) {
                bw.write(Long.toString(aLong));
                bw.write(System.lineSeparator());
            }
            bw.flush();
        }
        context.sendMessage("CSV文件已导出至 " + file.getAbsolutePath());
    }

    private void print2Screen(List<Long> longs, Context context) throws Exception {
        int digits = digits(longs.size() - 1);
        for (int i = 0; i < longs.size(); i++) {
            context.sendMessage(String.format("%-" + digits + "d: %d", i, longs.get(i)));
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
