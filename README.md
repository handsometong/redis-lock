redis分布式锁
使用方法：
直接在方法上增加 @RedisAction 注解 （支持spring El表达式）
`@RedisAction("'test'.concat(#user.id)")
public void update(UserVO user){
try {
Thread.sleep(5000);
} catch (InterruptedException e) {
logger.error("exp", e);
}
}`

注解参数说明
`@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface RedisAction {

/** 锁的资源，key。支持spring El表达式*/
@AliasFor("key")
String value() default "'default'";

@AliasFor("value")
String key() default "'default'";

/** 持锁时间,单位毫秒*/
long keepMills() default 30000;

/** 当获取失败时候动作*/
LockFailAction action() default LockFailAction.CONTINUE;

public enum LockFailAction{
/** 放弃 */
GIVEUP,
/** 继续 */
CONTINUE;
}

/** 重试的间隔时间,设置GIVEUP忽略此项*/
long sleepMills() default 200;

    /** 重试次数*/
    int retryTimes() default 5;
}

`