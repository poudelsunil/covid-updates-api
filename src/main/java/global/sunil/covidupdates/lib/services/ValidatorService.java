package global.sunil.covidupdates.lib.services;

/**
 * @author Sunil on 2021-05-25 - १६:३९
 */
public interface ValidatorService<T extends ServiceObject> {
    void validate(T info);
}