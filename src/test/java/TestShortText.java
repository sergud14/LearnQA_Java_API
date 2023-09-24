import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
public class TestShortText{
    @Test
    public void testSortText() {
        String text="Text length is more than 15 symbols";
        //String text="Short text";
        assertTrue(text.length()>=15,"Unexpected text length");
        }
    }
