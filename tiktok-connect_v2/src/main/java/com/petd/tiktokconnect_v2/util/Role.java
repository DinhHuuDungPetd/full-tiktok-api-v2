package com.petd.tiktokconnect_v2.util;

  public enum Role {

    Admin("role/Admin"),
    Employee("role/Employee"),
    Owner("role/Owner");

    private final String role;

    Role(String role){
      this.role = role;
    }

    public static Role fromValue(String input) {
      for (Role r : values()) {
        if (r.role.equalsIgnoreCase(input)) {
          return r;
        }
      }
      throw new IllegalArgumentException("Unknown role: " + input);
    }

  }
