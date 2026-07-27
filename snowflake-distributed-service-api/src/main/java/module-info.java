module com.dwarfeng.sfds.api {

    requires com.dwarfeng.sfds.stack;
    requires com.dwarfeng.subgrade.basic;
    requires spring.core;
    requires spring.beans;
    requires spring.context;
    requires spring.aop;

    exports com.dwarfeng.sfds.api.integration.subgrade;

    opens com.dwarfeng.sfds.api.integration.subgrade to spring.core, spring.beans, spring.context, spring.aop;
}
