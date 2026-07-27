module com.dwarfeng.sfds.node.all.he {

    requires com.dwarfeng.sfds.stack;
    requires com.dwarfeng.sfds.sdk;
    requires com.dwarfeng.sfds.impl;
    requires com.dwarfeng.subgrade.basic;
    requires com.dwarfeng.subgrade.aop;
    requires com.dwarfeng.springtelqos.core;
    requires com.dwarfeng.springtelqos.api;
    requires com.dwarfeng.springterminator.core;
    requires com.dwarfeng.springterminator.api;
    requires java.sql;
    requires jakarta.annotation;
    requires com.fasterxml.jackson.databind;
    requires spring.core;
    requires spring.beans;
    requires spring.context;
    requires spring.expression;
    requires spring.aop;
    requires org.aspectj.weaver;
    requires org.apache.commons.logging;
    requires dubbo;

    exports com.dwarfeng.sfds.node.all.he.configuration;
    exports com.dwarfeng.sfds.node.all.he.launcher;

    opens com.dwarfeng.sfds.node.all.he.configuration to spring.core, spring.beans, spring.context, spring.aop;
}
