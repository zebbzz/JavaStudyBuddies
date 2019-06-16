package javastudybuddies.discordbots;

import javastudybuddies.discordbots.welcomebot.Answer;

import java.util.*;

public class DiscordUser {
        Map<String, Answer<?>> unanswered;
        Map<String, Answer<?>> answered;

        private String id;
        private String tag;

        private String chatId;
        private ArrayList<LeetcodeTask> solved;

        public DiscordUser()  {
                unanswered = new HashMap<>();
                answered = new HashMap<>();

                unanswered.put("username", null);
                unanswered.put("level", null);
                unanswered.put("country", null);
                unanswered.put("timezone", null);
                unanswered.put("age", null);
                unanswered.put("goal", null);
                unanswered.put("tech", null);
        }

        public void answer(String question, Answer<?> answer)  {
                if (unanswered.keySet().contains(question))  {
                    unanswered.remove(question);
                }

                answered.put(question, answer);
        }

        public boolean allAnswered()  {return unanswered.size()==0;}
        public Set<String> questionsToAnswer()  {return unanswered.keySet();}

    public static void main(String[] args) {
        DiscordUser user = new DiscordUser();
        user.set("username", "someone");
        System.out.println(user.getName());
    }

        //getters
        public String getName() {return (String) answered.get("username").getAnswer();}
        public List<LeetcodeTask> getLeetcodeTasks()  {return solved;}
        public String getLevel()  {return (String) answered.get("level").getAnswer();}
        public String getCountry()  {return (String) answered.get("country").getAnswer();}
        public String getTimezone()  {return (String) answered.get("timezone").getAnswer();}
        public String getChatId()  {return chatId;}
        public int getAge() {return (Integer) answered.get("age").getAnswer();}
        public String getId()  {return id;}
        public String getTag()  {return tag;}
        public String getGoal()  {return (String) answered.get("goal").getAnswer();}
        public String getTech()  {return (String) answered.get("tech").getAnswer();}

        public Map<String, Answer<?>> getAnswered()  {return answered;}

        //setters
        public void setTech(String tech)  {this.answer("tech", new Answer<>("tech", tech));}
        public void setGoal(String goal)  {this.answer("goal", new Answer<>("goal", goal));}
        public void setTag(String tag)  {this.tag = tag; }
        public void setId(String id)  {this.id = id;}
        public void setName(String username)  {this.answer("username",
                                                                new Answer("username", username));}
        public void setLevel(String level)  {this.answer("level",
                                                                new Answer("level", level));}
        public void setCountry(String country)  {this.answer("country",
                                                                new Answer("country", country));}
        public void setTimezone(String timezone)  {this.answer("timezone",
                                                                new Answer("timezone", timezone));}
        public void setChatId(String chatId)  {this.chatId = chatId;}
        public void setAge(int age)  {this.answer("age", new Answer("age", age));}

        public void set(String type, String answer)  {
            switch (type)  {
                case "level":
                    setLevel(answer);
                    break;
                case "age":
                    setAge(Integer.parseInt(answer));
                    break;
                case "timezone":
                    setTimezone(answer);
                    break;
                case "country":
                    setCountry(answer);
                    break;
                case "username":
                    setName(answer);
                    break;
                case "goal":
                    setGoal(answer);
                case "tech":
                    setTech(answer);
                default:
                    System.out.println("No such type: " + type);
            }
        }

        public Object get(String type)  {
            if (type.equalsIgnoreCase("id"))  {
                return id;
            }

            if (type.equalsIgnoreCase("tag"))  {
                return tag;
            }

            return answered.get(type);
        }
}
