package com.dwarfeng.sfds.impl.service.telqos;

import com.dwarfeng.sfds.stack.bean.dto.ResolveResult;
import com.dwarfeng.sfds.stack.service.ResolveQosService;
import com.dwarfeng.springtelqos.sdk.command.CliCommand;
import com.dwarfeng.springtelqos.sdk.configuration.TelqosCommand;
import com.dwarfeng.springtelqos.sdk.util.CliCommandUtil;
import com.dwarfeng.springtelqos.stack.command.CommandDescriptor;
import com.dwarfeng.springtelqos.stack.command.CommandExecutor;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 解析指令。
 *
 * @author DwArFeng
 * @since 1.8.1
 */
@TelqosCommand
public class ResolveCommand extends CliCommand {

    @SuppressWarnings({"SpellCheckingInspection", "GrazieInspectionRunner", "RedundantSuppression"})
    private static final String IDENTITY = "res";

    // region 指令选项

    private static final String COMMAND_OPTION_RESOLVE = "resolve";

    private static final String[] COMMAND_OPTION_ARRAY = new String[]{
            COMMAND_OPTION_RESOLVE
    };

    private static final String COMMAND_SUB_OPTION_ID = "id";

    // endregion

    private final ResolveQosService resolveQosService;

    public ResolveCommand(ResolveQosService resolveQosService) {
        super(IDENTITY);
        this.resolveQosService = resolveQosService;
    }

    @Override
    protected DescriptionProvider provideDescriptionProvider() {
        return context -> "解析 ID 操作";
    }

    @Override
    protected CliSyntaxProvider provideCliSyntaxProvider() {
        return this::cliSyntaxProvider;
    }

    private String cliSyntaxProvider(CommandDescriptor.Context context) throws Exception {
        String identity = context.getRuntimeIdentity();
        String[] patterns = new String[]{
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_RESOLVE) + " [" +
                        CliCommandUtil.concatOptionPrefix(COMMAND_SUB_OPTION_ID) + " id]"
        };
        return CliCommandUtil.cliSyntax(patterns);
    }

    @Override
    protected List<Option> provideOptions() {
        List<Option> list = new ArrayList<>();

        list.add(Option.builder(COMMAND_OPTION_RESOLVE).optionalArg(true).hasArg(false).desc("解析 ID").build());

        list.add(
                Option.builder(COMMAND_SUB_OPTION_ID).desc("待解析的 ID").hasArg().type(Number.class).build()
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
            case COMMAND_OPTION_RESOLVE:
                handleResolve(context, cmd);
                break;
            default:
                throw new IllegalStateException("不应该执行到此处, 请联系开发人员");
        }
    }

    private void handleResolve(CommandExecutor.Context context, CommandLine cmd) throws Exception {
        long id = parseId(context, cmd);
        ResolveResult result = resolveQosService.resolveLong(id);
        printResult(result, context);
    }

    private long parseId(CommandExecutor.Context context, CommandLine cmd) throws Exception {
        if (cmd.hasOption(COMMAND_SUB_OPTION_ID)) {
            Number idNumber = (Number) cmd.getParsedOptionValue(COMMAND_SUB_OPTION_ID);
            if (Objects.isNull(idNumber)) {
                context.sendMessage("请输入待解析的 ID:");
                return Long.parseLong(context.receiveMessage());
            } else {
                return idNumber.longValue();
            }
        } else {
            context.sendMessage("请输入待解析的 ID:");
            return Long.parseLong(context.receiveMessage());
        }
    }

    private void printResult(ResolveResult result, CommandExecutor.Context context) throws Exception {
        if (result == null) {
            context.sendMessage("解析结果: null");
        } else {
            context.sendMessage("解析结果: ");
            context.sendMessage("  originalId: " + result.getOriginalId());
            context.sendMessage("  sequence: " + result.getSequence());
            context.sendMessage("  workerId: " + result.getWorkerId());
            context.sendMessage("  datacenterId: " + result.getDatacenterId());
            context.sendMessage("  timestampDelta: " + result.getTimestampDelta());
            context.sendMessage("  timestamp: " + result.getTimestamp());
            context.sendMessage("  twepoch: " + result.getTwepoch());
        }
    }
}
