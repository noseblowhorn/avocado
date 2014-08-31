package avocado;

import net.sf.cglib.proxy.Enhancer;

import java.util.HashMap;
import java.util.Map;

public class Mock {

    public static ThreadLocal<Map<Object, MockTracker>> mockTrackerMap = new ThreadLocal<Map<Object, MockTracker>>() {
        @Override
        protected Map<Object, MockTracker> initialValue() {
            return new HashMap<Object, MockTracker>();
        }
    };

    public static <T> T create(Class<T> clazz) {
        Enhancer enhancer = new Enhancer();
        MockTracker mockTracker = new MockTracker();
        enhancer.setCallback(mockTracker);
        enhancer.setSuperclass(clazz);
        T proxy = (T)enhancer.create();
        mockTrackerMap.get().put(proxy, mockTracker);

        return proxy;
    }

    public static MockEnhancer get(Object proxy) {
        MockTracker tracker = mockTrackerMap.get().get(proxy);
        if (tracker == null) {
            throw new IllegalArgumentException("The object " + proxy.toString() + " is not a proxy tracked by Avocado.");
        }

        return new MockEnhancer(tracker);
    }
}
