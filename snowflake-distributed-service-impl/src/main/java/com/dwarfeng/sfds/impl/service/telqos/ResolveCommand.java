package com.dwarfeng.sfds.impl.service.telqos;

import com.dwarfeng.sfds.stack.bean.dto.ResolveResult;
import com.dwarfeng.sfds.stack.service.ResolveQosService;
import com.dwarfeng.springtelqos.node.config.TelqosCommand;
import com.dwarfeng.springtelqos.sdk.command.CliCommand;
import com.dwarfeng.springtelqos.stack.command.Context;
import com.dwarfeng.springtelqos.stack.exception.TelqosException;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@TelqosCommand
public class ResolveCommand extends CliCommand {

    private static final String IDENTITY = "res";
    private static final String DESCRIPTION = "解析 ID 操作";

    private static final String COMMAND_OPTION_RESOLVE = "resolve";

    private static final String[] COMMAND_OPTION_ARRAY = new String[]{
            COMMAND_OPTION_RESOLVE
    };

    private static final String COMMAND_OPTION_ID = "id";

    private static final String CMD_LINE_SYNTAX_RESOLVE = IDENTITY + " " +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_RESOLVE) + " [" +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_ID) + " id]";

    private static final String[] CMD_LINE_ARRAY = new String[]{
            CMD_LINE_SYNTAX_RESOLVE
    };

    private static final String CMD_LINE_SYNTAX = CommandUtil.syntax(CMD_LINE_ARRAY);

    private final ResolveQosService resolveQosService;

    public ResolveCommand(ResolveQosService resolveQosService) {
        super(IDENTITY, DESCRIPTION, CMD_LINE_SYNTAX);
        this.resolveQosService = resolveQosService;
    }

    @Override
    protected List<Option> buildOptions() {
        List<Option> list = new ArrayList<>();
        list.add(Option.builder(COMMAND_OPTION_RESOLVE).desc("解析 ID").build());
        list.add(
                Option.builder(COMMAND_OPTION_ID).desc("待解析的 ID").hasArg().type(Number.class).build()
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
                case COMMAND_OPTION_RESOLVE:
                    handleResolve(context, cmd);
                    break;
                default:
                    throw new IllegalStateException("不应该执行到此处, 请联系开发人员");
            }
        } catch (Exception e) {
            throw new TelqosException(e);
        }
    }

    private void handleResolve(Context context, CommandLine cmd) throws Exception {
        long id = parseId(context, cmd);
        ResolveResult result = resolveQosService.resolveLong(id);
        printResult(result, context);
    }

    private long parseId(Context context, CommandLine cmd) throws Exception {
        if (cmd.hasOption(COMMAND_OPTION_ID)) {
            Number idNumber = (Number) cmd.getParsedOptionValue(COMMAND_OPTION_ID);
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

    private void printResult(ResolveResult result, Context context) throws Exception {
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

