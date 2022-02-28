package com.example.c196.ViewModel;

public enum StatusOfCourse {

    COURSES_TO_TAKE{
        @Override
        public String toString() {
            return "Plan to take";
        }
    },
    IN_PROGRESS{
        @Override
        public String toString() {
            return "In Progress";
        }
    },
    DROPPED{
        @Override
        public String toString() {
            return "Dropped";
        }
    },

    COMPLETED{
        @Override
        public String toString() {
            return "Completed";
        }
    }
}
