package com.example.nutritive_app.dto.request

enum class AssignRoleRequest(val roleId: Int) {
    READER(1),
    CREATOR(2),
    EDITOR(3),
    ADMIN(4)
}