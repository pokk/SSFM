package taiwan.no1.app.ssfm.internal.di.annotations

import javax.inject.Scope

/**
 * A scoping annotation to permit objects whose lifetime should conform to the life of the fragment to be
 * memorized in the correct component.
 *
 * @author  jieyi
 * @since   5/9/17
 */
@Scope
@Retention
annotation class PerFragment