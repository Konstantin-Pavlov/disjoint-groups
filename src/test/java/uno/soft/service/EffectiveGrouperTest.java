package uno.soft.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import uno.soft.service.fast_and_correct.EffectiveGrouper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EffectiveGrouperTest {
    private static EffectiveGrouper grouper;
    private static List<String> lines;

    @BeforeAll
    static void setUp() {
        grouper = new EffectiveGrouper();
    }

    @Test
    void groupLinesTest() {
        // Prepare a list of lines
        lines = Arrays.asList(
                "\"111\";\"123\";\"222\"",
                "\"200\";\"123\";\"100\"",
                "\"300\";;\"100\"",
                "\"400\";\"456\";\"300\""
        );

        // Expected groups:
        // - Group 1: Lines 1, 2, 3 (sharing "123" in the second column and "100" in the third column)
        // - Group 2: Line 4 (no shared values with other lines)

        // Call the method to group the lines
        List<List<String>> result = grouper.findLineGroups(lines);

        // Prepare the expected groups
        List<String> expectedGroup1 = new ArrayList<>(Arrays.asList(
                "\"111\";\"123\";\"222\"",
                "\"200\";\"123\";\"100\"",
                "\"300\";;\"100\""
        )) {
        };

        List<String> expectedGroup2 = new ArrayList<>(Collections.singletonList(
                "\"400\";\"456\";\"300\""
        ));

        // Expected result should have two groups
        assertEquals(2, result.size(), "The number of groups should be 2");

        // Check that both expected groups are present in the result
        assertTrue(result.contains(expectedGroup1), "Group 1 should be present");
        assertTrue(result.contains(expectedGroup2), "Group 2 should be present");

        print(result);
    }

    @Test
    void groupLinesTestWithLargeDataSet() {
        // Prepare a large list of lines
        lines = Arrays.asList(
                "\"\";\"79076513686\";\"79499289445\";\"79895211259\";\"79970144607\";\"79460148141\";\"79124811542\";\"79660572200\";\"79245307223\";\"79220239511\"",
                "\"\";\"\";\"\";\"79587024342\";\"79373191100\";\"79297325003\";\"\";\"79082510931\";\"79407103756\";\"79218299774\";\"79297376383\"",
                "\"79748671113\";\"\";\"79448126993\"",
                "\"79675340418\";\"79874543026\";\"79020399615\";\"79223059005\";\"79895375164\";\"79964151960\";\"79121625654\";\"79168487484\";\"79177456020\"",
                "\"79578025704\";\"79290456698\";\"79822872594\"",
                "\"79282658856\";\"\";\"79723702292\";\"79815334258\";\"79667357621\";\"79547897526\";\"79569882751\"",
                "\"79899216253\";\"79321885889\";\"79823519313\";\"79187368421\";\"79967298407\";\"79495156330\";\"79806628428\";\"79751719169\"",
                "\"79846271511\";\"\";\"79905817365\";\"\";\"79385535785\";\"79975516840\";\"79409433153\";\"79340443814\";\"79579748263\"",
                "\"79055094466\";\"\";\"79139716599\";\"79089203411\";\"79286102508\"",
                "\"79338123053\";\"79819414725\";\"\";\"79777807599\";\"79468370712\";\"79721107967\";\"\"",
                "\"79827205630\";\"79737863494\";\"79170966130\";\"79535129766\";\"79671169110\";\"79486145175\";\"79676293109\";\"79789004263\"",
                "\"79291306455\";\"\";\"79239976350\";\"79386433459\";\"79673708641\"",
                "\"79603902316\";\"79025698704\";\"79822872594\"",
                "\"\";\"79109995486\";\"79227201929\"",
                "\"79087403366\";\"\"",
                "\"79458129356\";\"79291007012\";\"\";\"79604136336\";\"79724149874\";\"79355757113\";\"79692047967\";\"79851264496\";\"79104827431\";\"79330997199\"",
                "\"79127167011\";\"79615991357\";\"79896788128\";\"79457698599\";\"79700368360\"",
                "\"79202455935\";\"79009753892\";\"79305405434\";\"79335532768\";\"79706101911\";\"79723195078\"",
                "\"79586834681\";\"79116519607\";\"79574729172\";\"79743490976\";\"79019983508\";\"79141965474\"",
                "\"79585133316\";\"79435467510\"",
                "\"79014028008\";\"79993912833\";\"79384522983\";\"79669718787\";\"79682919735\";\"79386675859\";\"79833641284\";\"79977118650\";\"79940586248\";\"79812349123\"",
                "\"79206435634\";\"79951928099\";\"79681641915\"",
                "\"79249365746\";\"79415286123\";\"79094905624\";\"\";\"79904478778\";\"79445523945\"",
                "\"79479522619\";\"79250376825\";\"\";\"79976797681\"",
                "\"79537703049\";\"79340813072\";\"79041428185\";\"79993609925\";\"79132027378\";\"79303896705\";\"79717497010\";\"79895520758\";\"79361028834\";\"79484193054\"",
                "\"79363111783\";\"79076190622\";\"79041930471\";\"79860121645\";\"79832696022\"",
                "\"79209752664\";\"\";\"\";\"79834061171\";\"79448747721\";\"79260090940\"",
                "\"79894897096\";\"79514851623\"",
                "\"79007257076\";\"79084418081\"",
                "\"79698215102\";\"\";\"79461242732\";\"79527988381\";\"79300589024\";\"\";\"79899838375\";\"79634150039\";\"79513343152\";\"79538783814\"",
                "\"79392512581\";\"79173504485\";\"79129523115\";\"79994038188\";\"79755421176\";\"79987915762\";\"79472606458\";\"79084324732\";\"79560297632\"",
                "\"79182149317\";\"79869026357\";\"79111170171\";\"79375869767\";\"79537685051\";\"79859045941\";\"79553350262\";\"79868660361\";\"79444832084\"",
                "\"79819806058\";\"79931896365\";\"\";\"79537122329\";\"79156605221\";\"79461669835\";\"79050543516\";\"\";\"79402007874\";\"79280038213\"",
                "\"79818428218\";\"79151247935\"",
                "\"79511413078\";\"79231738880\";\"79800237125\"",
                "\"79282817570\";\"79726639700\";\"79281234492\";\"79512221211\";\"79914644320\";\"79780939295\";\"79054216727\"",
                "\"79453473167\";\"79236655428\";\"79354014229\";\"79212296315\";\"79735079667\";\"79171619912\";\"79122235342\";\"79349427403\";\"79108152619\";\"\"",
                "\"79477711176\";\"79596869187\";\"79890576352\";\"79100732120\";\"79682067901\";\"79263285202\";\"79552371341\";\"79486168784\";\"79755698092\"",
                "\"79721979057\";\"79235632672\";\"79816482949\";\"79881437257\";\"79340457873\";\"79537424194\";\"79586724251\";\"\";\"\"",
                "\"79330789662\";\"79113329681\";\"79157625640\";\"79761756786\";\"79642449350\";\"79623170517\"",
                "\"79439983928\";\"79498389020\";\"79384522983\";\"79660183089\";\"79032226363\";\"79477752647\";\"79687425671\";\"79136311712\"",
                "\"79846319472\";\"79123932435\";\"79164290351\";\"79846273631\";\"79507176457\";\"79199547413\";\"\";\"79496950632\";\"79378641232\";\"\""
        );

        // Call the method to group the lines
        List<List<String>> result = grouper.findLineGroups(lines);

        List<String> expectedGroup = new ArrayList<>(Arrays.asList(
                "\"79014028008\";\"79993912833\";\"79384522983\";\"79669718787\";\"79682919735\";\"79386675859\";\"79833641284\";\"79977118650\";\"79940586248\";\"79812349123\"",
                "\"79439983928\";\"79498389020\";\"79384522983\";\"79660183089\";\"79032226363\";\"79477752647\";\"79687425671\";\"79136311712\""
        ));

        assertTrue(result.contains(expectedGroup), "Group should be present");

        print(result);

        // Since the expected groups are not provided, we will just check the number of groups
        // This is a placeholder assertion, you should replace it with the actual expected number of groups
        assertEquals(40, result.size(), "The number of groups should be 40 ");
    }

    private static void print(List<List<String>> result) {
        AtomicInteger counter = new AtomicInteger();

        // Optional: Print the groups to inspect
        result.forEach(group -> {
            System.out.println("Group:" + counter.incrementAndGet());
            group.forEach(System.out::println);
            System.out.println();
        });
    }
}
