package com.example.fv.judgement.app.model;


    public class Product {
        private int pid;
        private String name;
        private float price;
        private String desc;
        private int image;

        public Product(int pid, String name, float price, String desc, int image) {
            this.pid = pid;
            this.name = name;
            this.price = price;
            this.desc = desc;
            this.image = image;
        }

        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public float getPrice() {
            return price;
        }

        public void setPrice(float price) {
            this.price = price;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public int getImage() {
            return image;
        }

        public void setImage(int image) {
            this.image = image;
        }
    }


