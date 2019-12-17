package view;

import domain.user.Player;

import java.util.Scanner;

public class InputView {
    private static Scanner sc = new Scanner(System.in);

    public static String inputPlayerName() {
        System.out.println("\n[게임 세팅]");
        System.out.println("게임에 참여할 사람의 이름을 입력하세요.(쉼표 기준으로 분리)");
        return sc.nextLine();
    }

    public static int inputPlayerMoney(String name) {
        System.out.println(name + "의 배팅 금액은?");
        return sc.nextInt();
    }

    public static Character inputChoiceGetCard(Player player) {
        System.out.println(player.getName() + "는 한장의 카드를 더 받겠습니까?(예는 y, 아니오는 n)");
        return sc.next().charAt(0);
    }
}
