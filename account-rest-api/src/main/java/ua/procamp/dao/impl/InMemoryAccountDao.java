package ua.procamp.dao.impl;

import org.springframework.stereotype.Component;
import ua.procamp.dao.AccountDao;
import ua.procamp.exception.EntityNotFountException;
import ua.procamp.model.Account;

import java.util.*;

/**
 * {@link AccountDao} implementation that is based on {@link java.util.HashMap}.
 * <p>
 * todo: 1. Configure a component with name "accountDao"
 */
@Component("accountDao")
public class InMemoryAccountDao implements AccountDao {

    private Map<Long, Account> accountMap = new HashMap<>();
    private long freeId = 0L;

    @Override
    public List<Account> findAll() {
        return new ArrayList<>(accountMap.values());
    }

    @Override
    public Account findById(long id) {
        if (!accountMap.containsKey(id)) {
            throw new EntityNotFountException(String.format("Account with id %d not found", id));
        }
        return accountMap.get(id);
    }

    @Override
    public Account save(Account account) {
        Objects.requireNonNull(account);
        if (Objects.isNull(account.getId())) {
            account.setId(++freeId);
            accountMap.put(freeId, account);
        }
        return accountMap.get(account.getId());
    }

    @Override
    public void remove(Account account) {
        Objects.requireNonNull(account);
        Objects.requireNonNull(account.getId());
        if (!accountMap.containsKey(account.getId())) {
            throw new EntityNotFountException(String.format("Account with id %d not found", account.getId()));
        }
        accountMap.remove(account.getId());
    }

    public void clear() {
        accountMap.clear();
    }
}
