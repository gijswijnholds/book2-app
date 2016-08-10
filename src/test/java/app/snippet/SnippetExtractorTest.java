package app.snippet;

import static org.junit.Assert.fail;

import org.junit.Test;

public class SnippetExtractorTest {

    private static final String testCode = "namespace eu.sig.training.ch11\n" + "{\n"
        + "    public class MagicConstants\n" + "    {\n" + "\n" + "        public class Customer\n" + "        {\n"
        + "\n" + "            public Customer(int age)\n" + "            {\n" + "                this.Age = age;\n"
        + "            }\n" + "\n" + "            public int Age { get; set; }\n" + "        }\n" + "\n"
        + "        public class UseMagicConstants\n" + "        {\n" + "\n"
        + "            // tag::calculateFareMagicConstants[]\n"
        + "            float CalculateFare(Customer c, long distance)\n" + "            {\n"
        + "                float travelledDistanceFare = distance * 0.10f;\n" + "                if (c.Age < 12)\n"
        + "                {\n" + "                    travelledDistanceFare *= 0.25f;\n" + "                }\n"
        + "                else\n" + "                    if (c.Age >= 65)\n" + "                {\n"
        + "                    travelledDistanceFare *= 0.5f;\n" + "                }\n"
        + "                return 3.00f + travelledDistanceFare;\n" + "            }\n"
        + "            // end::calculateFareMagicConstants[]\n" + "\n" + "        }\n" + "\n"
        + "        public class DoNotUseMagicConstants\n" + "        {\n"
        + "            // tag::calculateFareDoNotUseMagicConstants[]\n"
        + "            private static readonly float BASE_RATE = 3.00f;\n"
        + "            private static readonly float FARE_PER_KM = 0.10f;\n"
        + "            private static readonly float DISCOUNT_RATE_CHILDREN = 0.25f;\n"
        + "            private static readonly float DISCOUNT_RATE_ELDERLY = 0.5f;\n"
        + "            private static readonly int MAXIMUM_AGE_CHILDREN = 12;\n"
        + "            private static readonly int MINIMUM_AGE_ELDERLY = 65;\n" + "\n"
        + "            float CalculateFare(Customer c, long distance)\n" + "            {\n"
        + "                float travelledDistanceFare = distance * FARE_PER_KM;\n"
        + "                if (c.Age < MAXIMUM_AGE_CHILDREN)\n" + "                {\n"
        + "                    travelledDistanceFare *= DISCOUNT_RATE_CHILDREN;\n" + "                }\n"
        + "                else\n" + "                    if (c.Age >= MINIMUM_AGE_ELDERLY)\n" + "                {\n"
        + "                    travelledDistanceFare *= DISCOUNT_RATE_ELDERLY;\n" + "                }\n"
        + "                return BASE_RATE + travelledDistanceFare;\n" + "            }\n"
        + "            // end::calculateFareDoNotUseMagicConstants[]\n" + "        }\n" + "    }\n" + "}";

    @Test
    public void testExtractSnippets() {
        fail("Not yet implemented");
    }

}
