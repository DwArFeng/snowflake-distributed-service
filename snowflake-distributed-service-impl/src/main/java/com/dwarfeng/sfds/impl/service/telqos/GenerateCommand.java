package com.dwarfeng.sfds.impl.service.telqos;

import com.dwarfeng.sfds.impl.internal.i18n.ImplMessageKey;
import com.dwarfeng.sfds.impl.internal.i18n.ImplMessages;
import com.dwarfeng.sfds.stack.service.GenerateQosService;
import com.dwarfeng.springtelqos.sdk.command.CliCommand;
import com.dwarfeng.springtelqos.sdk.configuration.TelqosCommand;
import com.dwarfeng.springtelqos.sdk.util.CliCommandUtil;
import com.dwarfeng.springtelqos.stack.command.CommandDescriptor;
import com.dwarfeng.springtelqos.stack.command.CommandExecutor;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.lang3.tuple.Pair;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 生成指令。
 *
 * @author DwArFeng
 * @since 1.4.7
 */
@TelqosCommand
public class GenerateCommand extends CliCommand {

    @SuppressWarnings({"SpellCheckingInspection", "GrazieInspectionRunner", "RedundantSuppression"})
    private static final String IDENTITY = "gen";

    // region 指令选项

    private static final String COMMAND_OPTION_GENERATE = "generate";

    private static final String[] COMMAND_OPTION_ARRAY = new String[]{
            COMMAND_OPTION_GENERATE
    };

    private static final String COMMAND_SUB_OPTION_SIZE = "s";
    private static final String COMMAND_SUB_OPTION_FILE = "f";

    // endregion

    // region 默认值

    private static final int DEFAULT_SIZE = 10;
    private static final String DEFAULT_FILE_NAME = "export.csv";

    // endregion

    private final GenerateQosService generateQosService;

    public GenerateCommand(GenerateQosService generateQosService) {
        super(IDENTITY);
        this.generateQosService = generateQosService;
    }

    @Override
    protected DescriptionProvider provideDescriptionProvider() {
        return context -> ImplMessages.message(ImplMessageKey.TELQOS_GENERATE_DESCRIPTION);
    }

    @Override
    protected CliSyntaxProvider provideCliSyntaxProvider() {
        return this::cliSyntaxProvider;
    }

    private String cliSyntaxProvider(CommandDescriptor.Context context) throws Exception {
        String identity = context.getRuntimeIdentity();
        String[] patterns = new String[]{
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_GENERATE) + " [" +
                        CliCommandUtil.concatOptionPrefix(COMMAND_SUB_OPTION_SIZE) + " size] [" +
                        CliCommandUtil.concatOptionPrefix(COMMAND_SUB_OPTION_FILE) + " file-path]"
        };
        return CliCommandUtil.cliSyntax(patterns);
    }

    @Override
    protected List<Option> provideOptions() {
        List<Option> list = new ArrayList<>();

        list.add(Option.builder(COMMAND_OPTION_GENERATE).optionalArg(true).hasArg(false)
                .desc(ImplMessages.message(ImplMessageKey.TELQOS_GENERATE_OPTION_GENERATE)).get());

        list.add(
                Option.builder(COMMAND_SUB_OPTION_SIZE)
                        .desc(ImplMessages.message(ImplMessageKey.TELQOS_GENERATE_OPTION_SIZE))
                        .hasArg().type(Number.class).get()
        );
        list.add(
                Option.builder(COMMAND_SUB_OPTION_FILE)
                        .desc(ImplMessages.message(ImplMessageKey.TELQOS_GENERATE_OPTION_FILE))
                        .hasArg().type(String.class).get()
        );
        return list;
    }

    @SuppressWarnings("SwitchStatementWithTooFewBranches")
    @Override
    protected void executeWithCmd(CommandExecutor.Context context, CommandLine cmd) throws Exception {
        Pair<String, Integer> pair = CliCommandUtil.analyseCommand(cmd, COMMAND_OPTION_ARRAY);
        if (pair.getRight() != 1) {
            context.sendMessage(CliCommandUtil.optionMismatchMessage(COMMAND_OPTION_ARRAY));
            context.sendMessage(context.getCommandManual(context.getRuntimeIdentity()));
            return;
        }
        switch (pair.getLeft()) {
            case COMMAND_OPTION_GENERATE:
                handleGenerate(context, cmd);
                break;
            default:
                throw new IllegalStateException(ImplMessages.message(ImplMessageKey.TELQOS_INTERNAL_UNREACHABLE));
        }
    }

    private void handleGenerate(CommandExecutor.Context context, CommandLine cmd) throws Exception {
        int size = parseSize(context, cmd);
        List<Long> longs = generateQosService.generateLong(size);

        if (cmd.hasOption(COMMAND_SUB_OPTION_FILE)) {
            print2File(longs, context, cmd);
        } else {
            print2Screen(longs, context);
        }
    }

    private int parseSize(CommandExecutor.Context context, CommandLine cmd) throws Exception {
        if (cmd.hasOption(COMMAND_SUB_OPTION_SIZE)) {
            Number sizeNumber = cmd.getParsedOptionValue(COMMAND_SUB_OPTION_SIZE);
            if (Objects.isNull(sizeNumber)) {
                return DEFAULT_SIZE;
            } else {
                return sizeNumber.intValue();
            }
        } else {
            context.sendMessage(ImplMessages.message(ImplMessageKey.TELQOS_GENERATE_PROMPT_SIZE));
            return Integer.parseInt(context.receiveMessage());
        }
    }

    private void print2File(List<Long> longs, CommandExecutor.Context context, CommandLine cmd) throws Exception {
        File file;

        String optionFileName = cmd.getParsedOptionValue(COMMAND_SUB_OPTION_FILE);
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
                context.sendMessage(ImplMessages.message(
                        ImplMessageKey.TELQOS_GENERATE_PROMPT_OVERWRITE, file.getName()
                ));
                String option = context.receiveMessage().toUpperCase();
                if (Objects.equals(option, "Y")) {
                    break;
                } else if (Objects.equals(option, "N")) {
                    context.sendMessage(ImplMessages.message(ImplMessageKey.TELQOS_GENERATE_OPERATION_CANCELLED));
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
        context.sendMessage(ImplMessages.message(
                ImplMessageKey.TELQOS_GENERATE_EXPORT_SUCCEEDED, file.getAbsolutePath()
        ));
    }

    @SuppressWarnings("StringConcatenationInFormatCall")
    private void print2Screen(List<Long> longs, CommandExecutor.Context context) throws Exception {
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
