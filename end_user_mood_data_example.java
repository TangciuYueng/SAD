@Data
@Entity
public class UserMoodData {
    @Id
    private String userid;
    private Date date;
    private int moodcount;
    private Map<String, Integer> mood;
}

@Repository
public interface UserMoodDataRepository extends JpaRepository<UserMoodData, String> {
    UserMoodData findOneById(String id);
    Iterable<UserMoodData> mostRecent(int t):
}

public enum HttpStatus {
    OK(200, "OK"),
    CREATED(201, "Created"),
    ACCEPTED(202, "Accepted"),
    NO_CONTENT(204, "No Content"),
    BAD_REQUEST(400, "Bad Request"),
    UNAUTHORIZED(401, "Unauthorized"),
    FORBIDDEN(403, "Forbidden"),
    NOT_FOUND(404, "Not Found"),
    METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
    SERVICE_UNAVAILABLE(503, "Service Unavailable");
}


@RestController
@RequestMapping("/api/emotion-analysis")
public class UserMoodDataController {

    @Autowired
    private UserMoodDataRepository userMoodDataRepository;

    @GetMapping("/history/{id}")
    public ResponseEntity<?> getUserMoodDataById(@PathVariable String userid) {
        try {
            UserMoodData moodData = userMoodDataRepository.findOneById(userid);
            if (moodData != null) {
                return new ResponseEntity<>(moodData, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("User mood data not found", HttpStatus.NOT_FOUND);
            }
        } 
        catch (Exception e) {
            return new ResponseEntity<>("Error retrieving emotion data", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}