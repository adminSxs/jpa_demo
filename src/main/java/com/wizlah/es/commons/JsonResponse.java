package com.wizlah.es.commons;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class JsonResponse {

    private int code = ErrorCode.SUCCESS.getCode();
    private String msg = ErrorCode.SUCCESS.getMessage();

    private boolean success = true;
    private Object data;
    private List <String> paramValidations = Lists.newArrayList();
    private Page page;

    private ErrorCode error = ErrorCode.SUCCESS;

    private SimplePropertyPreFilter filter = new SimplePropertyPreFilter();

    public void setIgnoreFilter(String... fields) {
        filter.getExcludes().addAll(Sets.newHashSet(fields));
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getData() {
        return JSON.toJSONString(this.data);
    }

    public List <String> getParamValidations() {
        return paramValidations;
    }

    public int getCode() {
        return this.code;
    }

    public void setPaging(Page page) {
        this.page = page;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setError(ErrorCode error) {
        this.code = error.getCode();
        this.msg = error.getMessage();
        this.error = error;
        this.success = false;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        Map <String, Object> map = Maps.newHashMap();
        if (page != null) {
            map.put("page", page);
        }
        if (paramValidations.size() != 0) {
            map.put("paramValidations", paramValidations);
        }
        if (Objects.equal(null, data)) {
            map.put("data", Maps.newHashMap());
        } else {
            map.put("data", data);
        }
        map.put("msg", error.getMessage());
        map.put("code", error.getCode());
        map.put("success", success);
        map.put("version", Version.CURRENT_VERSION.getVersion());
        return JSON.toJSONString(
                map, filter, SerializerFeature.WriteMapNullValue, SerializerFeature.UseISO8601DateFormat);
    }

    public void toResponse(HttpServletResponse response) {
        response.setHeader("Content-Type", "application/json");
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            JsonResponse jsonResponse = new JsonResponse();
            jsonResponse.setError(ErrorCode.OUTPUT_STREAM);
            outputStream.write(toString().getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
