package com.mfeia.book.server_automaiton.make_money;

import com.mfeia.book.server_automaiton.TestFrame;
import net.sf.json.JSONObject;

import java.util.Map;

/**
 * 上报任务状态(时长任务)(在领取时长任务后向服务端获取激活任务ID)
 */
public class TaskStatusUpdate extends TestFrame {
    public TaskStatusUpdate(JSONObject jsonObject) {
        super(jsonObject);
    }

    @Override
    public void settingJsonArrayMap(Map<String, Object> jsonArrayMap) {

    }

    @Override
    public void settingJsonMap(Map<String, Object> jsonMap) {
        jsonMap.put("code", 0);
    }

    @Override
    public Object customCheckJsonArrayObject(Object object, int index, String key, int size) {
        return null;
    }

    @Override
    public void customCheck() {

    }
}
