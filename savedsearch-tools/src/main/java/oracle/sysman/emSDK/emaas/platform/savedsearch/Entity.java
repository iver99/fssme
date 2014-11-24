package oracle.sysman.emSDK.emaas.platform.savedsearch;

import javax.xml.bind.annotation.XmlElement;

public abstract class Entity {
    private String id; 
       
    public void setId(String id) {
        this.id = id;
    }

    @XmlElement(name = "Id")
    public String getId() {
        return id;
    }
    public Entity() {
        super();
    }
}

