import org.example.aspect.BankingService;

@SpringBootTest
class BankingServiceTest {

    @Autowired
    private BankingService bankingService;

    @Test
    void testSecurityAndAuditAspect() {
        // This should trigger @Before (Security) and @AfterReturning (Audit)
        bankingService.transfer("AccountA", "AccountB", 500.0);
    }

    @Test
    void testSecurityFailure() {
        // This should trigger @Before and then @AfterThrowing because of the amount
        assertThrows(SecurityException.class, () -> {
            bankingService.transfer("AccountA", "AccountB", 20000.0);
        });
    }

    @Test
    void testRetryLogic() {
        // This calls the method that simulates DB failures.
        // The @Around advice should intercept failures and retry up to 3 times.
        double balance = bankingService.getBalance("123");
        System.out.println("Final Balance: " + balance);
    }
}