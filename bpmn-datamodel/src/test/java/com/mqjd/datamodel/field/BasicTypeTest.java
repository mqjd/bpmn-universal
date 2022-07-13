package com.mqjd.datamodel.field;

import com.mqjd.datamodel.utils.JsonUtils;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BasicTypeTest {

    @Test
    public void fromJson() {
        BasicType basicType = JsonUtils.jsonToObject("\"string\"", BasicType.class);
        assertEquals(BasicType.STRING, basicType);
    }
}
