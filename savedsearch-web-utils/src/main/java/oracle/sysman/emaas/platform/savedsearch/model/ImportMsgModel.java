package oracle.sysman.emaas.platform.savedsearch.model;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by chehao on 10/25/2017.
 */
public class ImportMsgModel {

    @JsonProperty("true")
    private boolean success;
    private String msg;

    public ImportMsgModel(boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
