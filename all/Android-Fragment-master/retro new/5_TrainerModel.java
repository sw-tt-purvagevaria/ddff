package com.pg.helloworld;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by test on 6/10/17.
 */

public class TrainerModel implements Serializable {


    @SerializedName("header")
    private Header header;
    @SerializedName("data")
    private Data data;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Header {
        @SerializedName("status")
        private int status;
        @SerializedName("message")
        private String message;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    public static class User_data {
        @SerializedName("today_rank")
        private String today_rank;
        @SerializedName("today_points")
        private String today_points;
        @SerializedName("total_month_points")
        private String total_month_points;

        public String getToday_rank() {
            return today_rank;
        }

        public void setToday_rank(String today_rank) {
            this.today_rank = today_rank;
        }

        public String getToday_points() {
            return today_points;
        }

        public void setToday_points(String today_points) {
            this.today_points = today_points;
        }

        public String getTotal_month_points() {
            return total_month_points;
        }

        public void setTotal_month_points(String total_month_points) {
            this.total_month_points = total_month_points;
        }
    }

    public static class Leaderboard {
        @SerializedName("rank")
        private int rank;
        @SerializedName("member_id")
        private String member_id;
        @SerializedName("name")
        private String name;
        @SerializedName("branch_name")
        private String branch_name;
        @SerializedName("cumulative_points")
        private String cumulative_points;
        @SerializedName("profile_pic")
        private String profile_pic;

        public int getRank() {
            return rank;
        }

        public void setRank(int rank) {
            this.rank = rank;
        }

        public String getMember_id() {
            return member_id;
        }

        public void setMember_id(String member_id) {
            this.member_id = member_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getBranch_name() {
            return branch_name;
        }

        public void setBranch_name(String branch_name) {
            this.branch_name = branch_name;
        }

        public String getCumulative_points() {
            return cumulative_points;
        }

        public void setCumulative_points(String cumulative_points) {
            this.cumulative_points = cumulative_points;
        }

        public String getProfile_pic() {
            return profile_pic;
        }

        public void setProfile_pic(String profile_pic) {
            this.profile_pic = profile_pic;
        }
    }

    public static class Data {
        @SerializedName("user_data")
        private User_data user_data;
        @SerializedName("leaderboard")
        private List<Leaderboard> leaderboard;

        public User_data getUser_data() {
            return user_data;
        }

        public void setUser_data(User_data user_data) {
            this.user_data = user_data;
        }

        public List<Leaderboard> getLeaderboard() {
            return leaderboard;
        }

        public void setLeaderboard(List<Leaderboard> leaderboard) {
            this.leaderboard = leaderboard;
        }
    }
}
