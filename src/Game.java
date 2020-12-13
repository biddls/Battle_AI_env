public class Game {
    int healthSol = 3;
    int healthZom = 1;
    int reach = 5;
    int scoreSol = 0;
    int scoreZom = 0;
    int damage = 1;
    int magazine = 8;

    public void handleHealth(int health, char Agent) {
        switch (Agent) {
            case 'A':
                healthSol -= health;
                System.out.println("agent A took" + health + "damage" );
                scoreZom += 1;
                break;
            case 'B':
                healthZom -= health;
                System.out.println("agent B took" + health + "damage" );
                scoreSol += 1;
                break;
        }

    }

    public void hit(Point AgentA, Point AgentB, char attacked) {

        if(AgentA.x - AgentB.x < reach && AgentA.y - AgentB.y < reach){
            handleHealth(damage,attacked);
        }
    }

    public void reset() {
        healthSol = 3;
        healthZom = 1;
        scoreSol = 0;
        scoreZom = 0;
    }

    public void shot(boolean hit, char attacked) {
        if(magazine == 0){
            int count = 0;
            while((count!= 100)){ // supposed to shoot soldier from shooting
                count++;
            }
            magazine = 8;
        }
        if (hit) {
            System.out.println("soldier hit" + attacked);
            magazine -= 1;
            handleHealth(damage, attacked);
        } else {
            System.out.println("soldier missed");
            magazine -= 1;
        }

    }


}
