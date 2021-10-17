package ru.sber.services.processors

import org.springframework.beans.factory.config.BeanFactoryPostProcessor
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.beans.factory.support.DefaultListableBeanFactory
import org.springframework.beans.factory.support.GenericBeanDefinition
import org.springframework.stereotype.Component
import ru.sber.services.BeanFactoryPostProcessorBean

@Component
class MyBeanFactoryPostProcessor : BeanFactoryPostProcessor {
    override fun postProcessBeanFactory(beanFactory: ConfigurableListableBeanFactory) {
        val beanDefinition = GenericBeanDefinition()
        beanDefinition.setBeanClass(BeanFactoryPostProcessorBean::class.java)
        beanDefinition.initMethodName = "postConstruct"
        (beanFactory as DefaultListableBeanFactory).registerBeanDefinition("beanFactoryPostProcessorBean", beanDefinition)
    }
}