package kr.song.vttest.models

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.*

@Entity
@Table(name = "user", schema = "public")
class UserEntity(
  @Id
  @Column(name = "id")
  var id: String = UUID.randomUUID().toString(),

  @Column(name = "name")
  var name: String,

  @Column(name = "email")
  var email: String
)
{
  constructor(vo: User) : this(
    id = vo.id,
    name = vo.name,
    email = vo.email
  )

  constructor() : this("", "", "")
}