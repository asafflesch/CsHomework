
/**
 * String Comparator compare Strings according to the lexicographical order of Strings.
 */
public class StringComparator implements Comparator {

	public int compare(Object o1, Object o2) {
                String str1 = o1.toString(), str2 = o2.toString();

                int minLength = str1.length(), ret = 0;
                if (str2.length() < minLength) minLength = str2.length();

                for (int i = 0; i < minLength && ret == 0; ++i)
                {
                    if (str1.charAt(i) < str2.charAt(i))
                        ret = -1;
                    else if (str1.charAt(i) > str2.charAt(i))
                        ret = 1;
                }

                if (ret == 0)
                {
                    if (str2.length() > minLength) ret = 1;
                    else if (str1.length() > minLength) ret = -1;
                }

		return ret;
	}

}
