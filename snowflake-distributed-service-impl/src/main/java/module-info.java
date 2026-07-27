module com.dwarfeng.sfds.impl {

    requires com.dwarfeng.sfds.base;
    requires com.dwarfeng.sfds.stack;
    requires com.dwarfeng.sfds.sdk;
    requires com.dwarfeng.subgrade.basic;
    requires com.dwarfeng.springtelqos.core;
    requires jakarta.annotation;
    requires org.apache.commons.cli;
    requires org.apache.commons.lang3;
    requires org.slf4j;
    requires spring.core;
    requires spring.beans;
    requires spring.context;
    requires spring.aop;

    exports com.dwarfeng.sfds.impl.handler;
    exports com.dwarfeng.sfds.impl.service;
    exports com.dwarfeng.sfds.impl.service.telqos;

    opens com.dwarfeng.sfds.impl.handler to spring.core, spring.beans, spring.context, spring.aop;
    opens com.dwarfeng.sfds.impl.service to spring.core, spring.beans, spring.context, spring.aop;
    opens com.dwarfeng.sfds.impl.service.telqos to spring.core, spring.beans, spring.context, spring.aop;
    opens com.dwarfeng.sfds.impl.i18n to com.dwarfeng.sfds.base;
}
