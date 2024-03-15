package bullscows.view;

public class ConsoleView implements View{

    @Override
    public void displayMessage(String s) {
        System.out.println(s);
    }

    @Override
    public void displayError(String errorMsg) {
        System.out.println(errorMsg);
    }
}
