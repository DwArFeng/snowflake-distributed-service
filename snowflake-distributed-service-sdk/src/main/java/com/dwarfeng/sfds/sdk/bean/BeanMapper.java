package com.dwarfeng.sfds.sdk.bean;

import com.dwarfeng.sfds.sdk.bean.dto.FastJsonResolveResult;
import com.dwarfeng.sfds.sdk.bean.dto.JSFixedFastJsonResolveResult;
import com.dwarfeng.sfds.stack.bean.dto.ResolveResult;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonLongIdKey;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputLongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

/**
 * Bean 映射器。
 *
 * <p>
 * 该映射器中包含了 <code>sdk</code> 模块中所有实体与 <code>stack</code> 模块中对应实体的映射方法。
 *
 * @author DwArFeng
 * @since 1.8.1
 */
@Mapper
public interface BeanMapper {

    // -----------------------------------------------------------Subgrade Key-----------------------------------------------------------
    FastJsonLongIdKey longIdKeyToFastJson(LongIdKey longIdKey);

    @InheritInverseConfiguration
    LongIdKey longIdKeyFromFastJson(FastJsonLongIdKey fastJsonLongIdKey);

    JSFixedFastJsonLongIdKey longIdKeyToJSFixedFastJson(LongIdKey longIdKey);

    @InheritInverseConfiguration
    LongIdKey longIdKeyFromJSFixedFastJson(JSFixedFastJsonLongIdKey jSFixedFastJsonLongIdKey);

    WebInputLongIdKey longIdKeyToWebInput(LongIdKey longIdKey);

    @InheritInverseConfiguration
    LongIdKey longIdKeyFromWebInput(WebInputLongIdKey webInputLongIdKey);

    // -----------------------------------------------------------Sfds DTO-----------------------------------------------------------
    FastJsonResolveResult resolveResultToFastJson(ResolveResult resolveResult);

    @InheritInverseConfiguration
    ResolveResult resolveResultFromFastJson(FastJsonResolveResult fastJsonResolveResult);

    JSFixedFastJsonResolveResult resolveResultToJSFixedFastJson(ResolveResult resolveResult);

    @InheritInverseConfiguration
    ResolveResult resolveResultFromJSFixedFastJson(JSFixedFastJsonResolveResult jSFixedFastJsonResolveResult);
}
