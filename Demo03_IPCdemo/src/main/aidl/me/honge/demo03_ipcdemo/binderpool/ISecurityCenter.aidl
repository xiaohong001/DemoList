package me.honge.demo03_ipcdemo.binderpool;

interface ISecurityCenter {
    String encrypt(String content);
    String decrypt(String password);
}
