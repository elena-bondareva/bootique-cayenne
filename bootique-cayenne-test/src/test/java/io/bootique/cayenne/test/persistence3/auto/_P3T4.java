package io.bootique.cayenne.test.persistence3.auto;

import java.util.List;

import org.apache.cayenne.CayenneDataObject;
import org.apache.cayenne.exp.Property;

import io.bootique.cayenne.test.persistence3.P3T1;

/**
 * Class _P3T4 was generated by Cayenne.
 * It is probably a good idea to avoid changing this class manually,
 * since it may be overwritten next time code is regenerated.
 * If you need to make any customizations, please use subclass.
 */
public abstract class _P3T4 extends CayenneDataObject {

    private static final long serialVersionUID = 1L; 

    public static final String ID_PK_COLUMN = "id";

    public static final Property<List<P3T1>> T1S = Property.create("t1s", List.class);

    public void addToT1s(P3T1 obj) {
        addToManyTarget("t1s", obj, true);
    }
    public void removeFromT1s(P3T1 obj) {
        removeToManyTarget("t1s", obj, true);
    }
    @SuppressWarnings("unchecked")
    public List<P3T1> getT1s() {
        return (List<P3T1>)readProperty("t1s");
    }


}
