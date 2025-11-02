package com.dwarfeng.sfds.impl.service.telqos;

import com.dwarfeng.sfds.stack.service.GenerateQosService;
import com.dwarfeng.springtelqos.node.config.TelqosCommand;
import com.dwarfeng.springtelqos.sdk.command.CliCommand;
import com.dwarfeng.springtelqos.stack.command.Context;
import com.dwarfeng.springtelqos.stack.exception.TelqosException;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.lang3.tuple.Pair;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@TelqosCommand
public class GenerateCommand extends CliCommand {

    private static final String IDENTITY = "gen";
    private static final String DESCRIPTION = "生成 ID 并导出到 CSV 操作";

    private static final String COMMAND_OPTION_GENERATE = "generate";

    private static final String[] COMMAND_OPTION_ARRAY = new String[]{
            COMMAND_OPTION_GENERATE
    };

    private static final String COMMAND_OPTION_SIZE = "s";
    private static final String COMMAND_OPTION_FILE = "f";

    private static final String CMD_LINE_SYNTAX_GENERATE = IDENTITY + " " +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_GENERATE) + " [" +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_SIZE) + " size] [" +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_FILE) + " file-path]";

    private static final String[] CMD_LINE_ARRAY = new String[]{
            CMD_LINE_SYNTAX_GENERATE
    };

    private static final String CMD_LINE_SYNTAX = CommandUtil.syntax(CMD_LINE_ARRAY);

    private static final int DEFAULT_SIZE = 10;
    private static final String DEFAULT_FILE_NAME = "export.csv";

    private final GenerateQosService generateQosService;

    public GenerateCommand(GenerateQosService generateQosService) {
        super(IDENTITY, DESCRIPTION, CMD_LINE_SYNTAX);
        this.generateQosService = generateQosService;
    }

    @Override
    protected List<Option> buildOptions() {
        List<Option> list = new ArrayList<>();
        list.add(Option.builder(COMMAND_OPTION_GENERATE).desc("生成 ID").build());
        list.add(
                Option.builder(COMMAND_OPTION_SIZE).desc("生成数量").hasArg().type(Number.class).build()
        );
        list.add(
                Option.builder(COMMAND_OPTION_FILE).desc("生成的 CSV 的路径").hasArg().type(String.class).build()
        );
        return list;
    }

    @SuppressWarnings("SwitchStatementWithTooFewBranches")
    @Override
    protected void executeWithCmd(Context context, CommandLine cmd) throws TelqosException {
        try {
            Pair<String, Integer> pair = CommandUtil.analyseCommand(cmd, COMMAND_OPTION_ARRAY);
            if (pair.getRight() != 1) {
                context.sendMessage(CommandUtil.optionMismatchMessage(COMMAND_OPTION_ARRAY));
                context.sendMessage(CMD_LINE_SYNTAX);
                return;
            }
            switch (pair.getLeft()) {
                case COMMAND_OPTION_GENERATE:
                    handleGenerate(context, cmd);
                    break;
                default:
                    throw new IllegalStateException("不应该执行到此处, 请联系开发人员");
            }
        } catch (Exception e) {
            throw new TelqosException(e);
        }
    }

    private void handleGenerate(Context context, CommandLine cmd) throws Exception {
        int size = parseSize(context, cmd);
        List<Long> longs = generateQosService.generateLong(size);

        if (cmd.hasOption(COMMAND_OPTION_FILE)) {
            print2File(longs, context, cmd);
        } else {
            print2Screen(longs, context);
        }
    }

    private int parseSize(Context context, CommandLine cmd) throws Exception {
        if (cmd.hasOption(COMMAND_OPTION_SIZE)) {
            Number sizeNumber = (Number) cmd.getParsedOptionValue(COMMAND_OPTION_SIZE);
            if (Objects.isNull(sizeNumber)) {
                return DEFAULT_SIZE;
            } else {
                return sizeNumber.intValue();
            }
        } else {
            context.sendMessage("请输入需要生成的 ID 的数量:");
            return Integer.parseInt(context.receiveMessage());
        }
    }

    private void print2File(List<Long> longs, Context context, CommandLine cmd) throws Exception {
        File file;

        String optionFileName = (String) cmd.getParsedOptionValue(COMMAND_OPTION_FILE);
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
        context.sendMessage("CSV 文件已导出至 " + file.getAbsolutePath());
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
