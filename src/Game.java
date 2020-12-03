public class Game {
    int healthA;
    int healthB;
    int reach;
    int scoreA;
    int scoreB;
    int damage;

    public void handleHealth(int health, char Agent) {
        switch (Agent) {
            case 'A':
                healthA += health;
                System.out.println("agent A took" + health + "damage" );
                scoreB += 1;
                break;
            case 'B':
                healthB += health;
                System.out.println("agent B took" + health + "damage" );
                scoreA += 1;
                break;
        }

    }

    public void hit(Point AgentA, Point AgentB, char attacked) {

        if(AgentA.x - AgentB.x < reach && AgentA.y - AgentB.y < reach){
            handleHealth(damage,attacked);
        }
    }

}
