package kr.song.vttest.models

data class User(
  val id: String,
  val name: String,
  val email: String
)
{
  constructor(entity: UserEntity) : this(
    id = entity.id,
    name = entity.name,
    email = entity.email
  )
}
