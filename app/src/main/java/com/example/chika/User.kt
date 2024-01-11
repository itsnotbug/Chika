package com.example.chika

class User {
    var name: String?= null
    private var email:String?= null
    private var uid:String?=null

    constructor(name:String?,email:String?,uid:String?){
      this.name = name
      this.email = email
      this.uid = uid

    }
}