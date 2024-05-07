class Computer {
}
public class ComputerSet {
    private Computer computer;
    private String keyboard, mouse, speaker, monitor;

    public Computer getComputer() {
        return computer;
    }

    public void setComputer(Computer computer) {
        this.computer = computer;
    }

    public String getKeyboard() {
        return keyboard;
    }

    public void setKeyboard(String keyboard) {
        this.keyboard = keyboard;
    }

    public String getMouse() {
        return mouse;
    }

    public void setMouse(String mouse) {
        this.mouse = mouse;
    }

    public String getSpeaker() {
        return speaker;
    }

    public void setSpeaker(String speaker) {
        this.speaker = speaker;
    }

    public String getMonitor() {
        return monitor;
    }

    public void setMonitor(String monitor) {
        this.monitor = monitor;
    }

}

class ComputerSetBuilder {
    private ComputerSet computerSet;

    public ComputerSetBuilder() {
        this(new ComputerSet());
    }

    public ComputerSetBuilder(ComputerSet computerSet) {
        this.computerSet = computerSet;
    }

    public void computer(Computer computer) {
        computerSet.setComputer(computer);
    }

    public void keyboard(String keyboard) {
        computerSet.setKeyboard(keyboard);
    }

    public void mouse(String mouse) {
        computerSet.setMouse(mouse);
    }

    public void speaker(String speaker) {
        computerSet.setSpeaker(speaker);
    }

    public void monitor(String monitor) {
        computerSet.setMonitor(monitor);
    }


    public ComputerSet getResult() {
        return computerSet;
    }

    public void resetBuilder() {
        computerSet = new ComputerSet();
    }

}

class Demo {
    public static void main(String[] args) {
        // Membuat sebuah instance dari builder
        ComputerSetBuilder builder = new ComputerSetBuilder();

        // Menetapkan nilai atribut
        builder.computer(new Computer());
        builder.keyboard("Mechanical");
        builder.mouse("Wireless");
        builder.speaker("Surround Sound");
        builder.monitor("27-inch 4K");

        // Mendapatkan objek ComputerSet yang telah dibangun
        ComputerSet computerSet = builder.getResult();

        // Melakukan sesuatu dengan objek ComputerSet yang telah dibangun
        // Contoh: mencetak nilai atribut
        System.out.println("Keyboard: " + computerSet.getKeyboard());
        System.out.println("Mouse: " + computerSet.getMouse());
        System.out.println("Speaker: " + computerSet.getSpeaker());
        System.out.println("Monitor: " + computerSet.getMonitor());
    }
}