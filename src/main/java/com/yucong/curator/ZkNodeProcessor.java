package com.yucong.curator;

import java.lang.reflect.Field;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.yucong.util.ZkUtil;

@Component
public class ZkNodeProcessor implements ApplicationContextAware, BeanPostProcessor {

    private ZkUtil zkUtil;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        zkUtil = applicationContext.getBean(ZkUtil.class);
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> clazz = bean.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            ZkNode zkNode = field.getAnnotation(ZkNode.class);
            if (zkNode != null) {
                String nodePath = zkNode.nodePath();
                if (StringUtils.isNotEmpty(nodePath)) {
                    try {
                        String value = zkUtil.findData(nodePath);
                        if (StringUtils.isEmpty(value)) {
                            value = zkNode.defaultValue();
                        }
                        if (!field.isAccessible()) {
                            field.setAccessible(true);
                        }
                        field.set(bean, ConvertUtils.convert(value, field.getType()));

                        if (zkNode.isWather()) {
                            addWatcher(field, bean, nodePath);
                        }
                    } catch (Exception e) {
                        System.out.println("========================= ZkNodeProcessor异常 ========================");
                    }
                }
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    private void addWatcher(final Field field, final Object bean, final String nodePath) throws Exception {
        zkUtil.addDataWatcher(nodePath, new ZkUtil.ZkDataOp() {
            @Override
            public void process(NodeCache nodeCache) throws Exception {
                String value = new String(nodeCache.getCurrentData().getData(), "gbk");
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                field.set(bean, ConvertUtils.convert(value, field.getType()));
            }
        });
    }

}
