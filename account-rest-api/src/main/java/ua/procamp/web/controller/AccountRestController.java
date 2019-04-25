package ua.procamp.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.procamp.dao.AccountDao;
import ua.procamp.exception.EntityNotFountException;
import ua.procamp.model.Account;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 * todo: 1. Configure rest controller that handles requests with url "/accounts"
 * todo: 2. Inject {@link AccountDao} implementation
 * todo: 3. Implement method that handles GET request and returns a list of accounts
 * todo: 4. Implement method that handles GET request with id as path variable and returns account by id
 * todo: 5. Implement method that handles POST request, receives account as request body, saves account and returns it
 * todo:    Configure HTTP response status code 201 - CREATED
 * todo: 6. Implement method that handles PUT request with id as path variable and receives account as request body.
 * todo:    It check if account id and path variable are the same and throws {@link IllegalStateException} otherwise.
 * todo:    Then it saves received account. Configure HTTP response status code 204 - NO CONTENT
 * todo: 7. Implement method that handles DELETE request with id as path variable removes an account by id
 * todo:    Configure HTTP response status code 204 - NO CONTENT
 */
@RestController
@RequestMapping(value = "/accounts")
public class AccountRestController {

    private AccountDao accountDao;

    public AccountRestController(final AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @GetMapping
    public List<Account> getAccounts() {
        return accountDao.findAll();
    }

    @GetMapping(path = "/{id}")
    public Account getAccount(@PathVariable Long id) {
        return accountDao.findById(id);
    }

    @PostMapping
    public ResponseEntity<Account> saveAccount(@RequestBody Account account) {
        return ResponseEntity.status(HttpStatus.CREATED).body(accountDao.save(account));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Account> updateAccount(@PathVariable Long id, @RequestBody Account account) {
        Objects.requireNonNull(id);
        Objects.requireNonNull(account);
        if (!id.equals(account.getId())) {
            throw new IllegalArgumentException("Path id doesn't match account id!");
        }
        accountDao.save(account);
        return ResponseEntity.noContent().build();
    }

    private boolean hasAccountWithId(Long id) {
        try {
            accountDao.findById(id);
        } catch (EntityNotFountException e) {
            return false;
        }
        return true;
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity deleteAccount(@PathVariable Long id) {
        Objects.requireNonNull(id);
        return ResponseEntity.noContent().build();
    }


}
