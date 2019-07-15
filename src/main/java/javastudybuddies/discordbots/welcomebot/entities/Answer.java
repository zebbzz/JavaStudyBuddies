package javastudybuddies.discordbots.welcomebot.entities;

public class Answer<V> {
        private String question;
        private V answer;

        public Answer(String question, V answer)  {
            this.question = question;
            this.answer = answer;
        }

        //getters
        public String getQuestion()  {return question;}
        public V getAnswer()  {return answer;}

        //setters
        public void setQuestion(String question)  {this.question = question;}
        public void setAnswer(V answer)  {this.answer = answer;}


}
