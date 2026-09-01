//package com.shrayu.entity;
//
//
//
//public enum Roles {
//
//    USER,
//
//    ADMIN,
//
//    MANAGER
//}

package com.shrayu.entity;

public enum Roles {

    USER(1),
    ADMIN(2),
    MANAGER(3);

    private final int code;

    Roles(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static Roles fromCode(Integer code) {

        if (code == null) {
            return null;
        }

        for (Roles role : Roles.values()) {
            if (role.code == code) {
                return role;
            }
        }

        throw new IllegalArgumentException(
                "Invalid role code: " + code
        );
    }
}

//UserCredential
//│
//└── Roles enum
//       ├── USER = 1
//       ├── ADMIN = 2
//       └── MANAGER = 3

//
//
//user_credentials
//----------------------------------
//id          username       role
//----------------------------------
//UUID        shubham         1 		//-user
//UUID        admin           2			//admin
//UUID        manager         3			//manager