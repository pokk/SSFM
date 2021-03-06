package taiwan.no1.app.ssfm.internal.di.annotations.scopes

import javax.inject.Scope

/**
 * A scoping annotation to permit objects whose lifetime should conform to the life of the fragment to be
 * memorized in the correct component.
 *
 * @author  jieyi
 * @since   6/8/17
 */
@Scope
@Retention
@MustBeDocumented
annotation class PerFragment